param(
    [string]$mysqlExe = "C:\\xampp\\mysql\\bin\\mysql.exe",
    [string]$mysqlUser = "root",
    [string]$mysqlPass = "",
    [int]$waitTimeoutSec = 60
)

# If no password was provided via parameter, prompt securely (hidden) and convert to plain text for the mysql CLI.
if ([string]::IsNullOrEmpty($mysqlPass)) {
    Write-Host "No MySQL password provided. If your root user has no password, just press Enter when prompted by the mysql client."
    $secure = Read-Host -AsSecureString "MySQL password (leave empty for none)"
    if ($secure.Length -gt 0) {
        $bstr = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)
        try {
            $mysqlPass = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($bstr)
        } finally {
            [System.Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr)
        }
    } else {
        $mysqlPass = ""
    }
}

# Paths (relative to repo root)
$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path | Split-Path -Parent
$usuarioPath = Join-Path $repoRoot "usuario-microservice"
$corePath = Join-Path $repoRoot "core-microservice"
$carritoPath = Join-Path $repoRoot "carrito-microservice"

# SQL files
$usuarioSql = Join-Path $usuarioPath "src\main\resources\cargaDatosInicial.sql"
$coreSql = Join-Path $corePath "src\main\resources\cargaDatosInicial.sql"
$carritoSql = Join-Path $carritoPath "src\main\resources\cargaDatosInicial.sql"

Write-Host "Starting microservices in new PowerShell windows..."
Start-Process powershell -ArgumentList '-NoExit','-Command',"cd '$usuarioPath'; .\\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Process powershell -ArgumentList '-NoExit','-Command',"cd '$corePath'; .\\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Process powershell -ArgumentList '-NoExit','-Command',"cd '$carritoPath'; .\\mvnw.cmd spring-boot:run" -WindowStyle Normal

# Wait for ports 8080, 8081, 8082
$ports = @{
    8080 = "usuario"
    8081 = "core"
    8082 = "carrito"
}

function Wait-ForPort([int]$port, [int]$timeoutSec){
    $start = Get-Date
    while ((New-TimeSpan -Start $start).TotalSeconds -lt $timeoutSec) {
        try {
            $r = Test-NetConnection -ComputerName 'localhost' -Port $port -WarningAction SilentlyContinue
            if ($r.TcpTestSucceeded) { return $true }
        } catch { }
        Start-Sleep -Seconds 2
    }
    return $false
}

foreach ($p in $ports.Keys) {
    Write-Host "Waiting for $($ports[$p]) service on port $p..."
    $ok = Wait-ForPort -port $p -timeoutSec $waitTimeoutSec
    if (-not $ok) {
        Write-Warning "Timeout waiting for port $p. You can still run the SQLs later."
    } else {
        Write-Host "$($ports[$p]) is up on port $p"
    }
}

# Build mysql command base
$mysqlCmdBase = "`"$mysqlExe`" -u $mysqlUser"
if ($mysqlPass -ne "") { $mysqlCmdBase += " -p$mysqlPass" } else { $mysqlCmdBase += " -p" }

# Execute SQL files (only if they exist)
function Exec-SqlFile([string]$sqlFile, [string]$dbName){
    if (-not (Test-Path $sqlFile)){
        Write-Warning "SQL file not found: $sqlFile"
        return
    }
    Write-Host "Seeding database $dbName using $sqlFile"
    $cmd = "$mysqlCmdBase $dbName < `"$sqlFile`""
    Write-Host "Running: $cmd"
    cmd /c $cmd
}

# Run the scripts
Exec-SqlFile -sqlFile $usuarioSql -dbName "bd_usuarios_pasteleria"
Exec-SqlFile -sqlFile $coreSql -dbName "bd_core_pasteleria"
Exec-SqlFile -sqlFile $carritoSql -dbName "bd_carrito_pasteleria"

Write-Host "Finished seeding. Verify in phpMyAdmin or in service logs."

Write-Host "If any SQL failed, run the specific command manually."
Write-Host "Example: & 'C:\\xampp\\mysql\\bin\\mysql.exe' -u root -p bd_core_pasteleria < .\\core-microservice\\src\\main\\resources\\cargaDatosInicial.sql"
