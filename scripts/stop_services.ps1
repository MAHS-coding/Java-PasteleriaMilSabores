param(
    [int[]]$ports = @(8080,8081,8082)
)

function Stop-ByPort_GetNetTCPConnection {
    param($port)
    try {
        $conns = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction Stop
    } catch {
        return $null
    }
    if ($conns) {
        $pids = $conns | Select-Object -ExpandProperty OwningProcess -Unique
        foreach ($ownPid in $pids) {
            try {
                Write-Host "Stopping PID $ownPid listening on port $port..."
                Stop-Process -Id $ownPid -Force -ErrorAction Stop
                Write-Host "Stopped PID $ownPid"
            } catch {
                Write-Warning "Failed to stop PID ${ownPid}: $($_)"
            }
        }
    }
}

function Stop-ByPort_Netstat {
    param($port)
    $lines = (& netstat -ano) -match ":$port\s"
    if (-not $lines) { return }
    foreach ($ln in $lines) {
        $parts = $ln -split '\s+' | Where-Object { $_ -ne '' }
        $ownPid = $parts[-1]
        if ($ownPid -and $ownPid -match '^[0-9]+$') {
            try {
                Write-Host "Killing PID $ownPid (found via netstat) for port $port..."
                Stop-Process -Id $ownPid -Force -ErrorAction Stop
                Write-Host "Killed PID $ownPid"
            } catch {
                Write-Warning "Failed to kill PID ${ownPid}: $($_)"
            }
        }
    }
}

foreach ($p in $ports) {
    Write-Host "Checking port $p..."
    $stopped = $false
    if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
        $conns = Get-NetTCPConnection -LocalPort $p -State Listen -ErrorAction SilentlyContinue
        if ($conns) {
            Stop-ByPort_GetNetTCPConnection -port $p
            $stopped = $true
        }
    }
    if (-not $stopped) {
        # Fallback to netstat parsing
        Stop-ByPort_Netstat -port $p
    }
    Write-Host "Done for port $p`n"
}

Write-Host "All requested ports processed. Verify services are stopped or check process list with Get-Process java."