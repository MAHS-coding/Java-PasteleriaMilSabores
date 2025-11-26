<#
Runs preparatory steps to apply Flyway migrations using XAMPP MySQL and then starts the two microservices so Flyway will apply schema + data (V1..V3).
Usage (defaults assume root / 1234):
.
# From PowerShell (run as normal user):
# .\scripts\run-with-xampp.ps1 -MySqlUser root -MySqlPassword 1234 -StartApps
#
# Parameters:
# -MySqlUser: MySQL username (default 'root')
# -MySqlPassword: MySQL password (default '1234')
# -BaselineOnMigrate: switch to set SPRING_FLYWAY_BASELINE_ON_MIGRATE=true when starting apps (use if database already has tables)
# -MySqlExePath: optional full-path to mysql.exe (if not in PATH). Common XAMPP path: C:\xampp\mysql\bin\mysql.exe
#
# Notes:
# - This script does NOT alter repository files. It only creates DBs and launches the apps.
# - Flyway migrations are included under each microservice in resources/db/migration and will run at app startup.
# - If mysql client is not available, the script will ask you to provide the path.
#
# Example:
# .\scripts\run-with-xampp.ps1 -MySqlUser root -MySqlPassword 1234 -StartApps -BaselineOnMigrate
#
#>
param(
    [string]$MySqlUser = 'root',
    [string]$MySqlPassword = '1234',
    [switch]$StartApps = $true,
    [switch]$BaselineOnMigrate = $false,
    [string]$MySqlExePath = ''
)

function Find-MySqlExe {
    param([string]$provided)
    if ($provided -and (Test-Path $provided)) { return $provided }

    # Try mysql in PATH
    $which = Get-Command mysql -ErrorAction SilentlyContinue
    if ($which) { return $which.Path }

    # Try common XAMPP path
    $possible = 'C:\xampp\mysql\bin\mysql.exe'
    if (Test-Path $possible) { return $possible }

    return $null
}

$mysql = Find-MySqlExe -provided $MySqlExePath
if (-not $mysql) {
    Write-Host "No mysql.exe found in PATH or default XAMPP location. Please provide -MySqlExePath path to your mysql.exe (e.g. C:\\xampp\\mysql\\bin\\mysql.exe)." -ForegroundColor Yellow
    exit 1
}

Write-Host "Using mysql client: $mysql"

# Test connection
Write-Host "Testing MySQL connection as user '$MySqlUser'..."
try {
    # Run a simple query. Note: pass password directly after -p without space so it doesn't prompt.
    & "$mysql" -u $MySqlUser -p$MySqlPassword -e "SELECT 1;" > $null 2>&1
    if ($LASTEXITCODE -ne 0) { throw "mysql returned exit code $LASTEXITCODE" }
    Write-Host "MySQL reachable."
} catch {
    Write-Host "Failed to connect to MySQL using provided credentials. Please start XAMPP MySQL and re-run this script, or provide correct credentials." -ForegroundColor Red
    exit 2
}

# Create databases
Write-Host "Creating databases if not exist: bd_core_pasteleria, bd_usuarios_pasteleria"
$query = "CREATE DATABASE IF NOT EXISTS bd_core_pasteleria; CREATE DATABASE IF NOT EXISTS bd_usuarios_pasteleria;"
& "$mysql" -u $MySqlUser -p$MySqlPassword -e $query
if ($LASTEXITCODE -ne 0) { Write-Host "Failed to create databases (mysql exit code $LASTEXITCODE)" -ForegroundColor Red; exit 3 }
Write-Host "Databases ready."

if ($StartApps) {
    # Build check - ensure mvnw exists in microservice folders
    $corePath = Join-Path -Path $PSScriptRoot -ChildPath "..\core-microservice"
    $usuarioPath = Join-Path -Path $PSScriptRoot -ChildPath "..\usuario-microservice"

    if (-not (Test-Path (Join-Path $corePath 'mvnw.cmd'))) {
        Write-Host "Warning: mvnw.cmd not found in core-microservice. Ensure you can start app manually or add mvnw to the repo root." -ForegroundColor Yellow
    }
    if (-not (Test-Path (Join-Path $usuarioPath 'mvnw.cmd'))) {
        Write-Host "Warning: mvnw.cmd not found in usuario-microservice. Ensure you can start app manually or add mvnw to the repo root." -ForegroundColor Yellow
    }

    # Prepare environment variables for baseline option
    $envVars = @{}
    if ($BaselineOnMigrate) { $envVars['SPRING_FLYWAY_BASELINE_ON_MIGRATE'] = 'true' }

    Write-Host "Starting microservices (each will open in a new PowerShell window). Logs will stay open."

    # Start core microservice in new window
    $coreCommand = "Set-Location -Path `"$($corePath)`"; .\\mvnw.cmd spring-boot:run"
    $coreArgs = @('-NoExit','-Command',$coreCommand)
    Start-Process -FilePath 'powershell.exe' -ArgumentList $coreArgs -WorkingDirectory $corePath -WindowStyle Normal -Verb RunAs

    # Start usuario microservice in new window
    $usuarioCommand = "Set-Location -Path `"$($usuarioPath)`"; .\\mvnw.cmd spring-boot:run"
    $usuarioArgs = @('-NoExit','-Command',$usuarioCommand)
    Start-Process -FilePath 'powershell.exe' -ArgumentList $usuarioArgs -WorkingDirectory $usuarioPath -WindowStyle Normal -Verb RunAs

    Write-Host "Started both services. Give them a minute and then check their logs. Flyway should run automatically on startup and apply migrations V1..V3."
}
else {
    Write-Host "Skipping starting apps (StartApps was not specified). Databases were created." -ForegroundColor Green
}

Write-Host "Done." -ForegroundColor Green
