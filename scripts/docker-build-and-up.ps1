<#
Builds the microservice Docker images and starts the stack using docker compose.
Usage:
  1. Copy `.env.example` to `.env` and fill `DB_PASS` and `JWT_SECRET`.
  2. Run this script in PowerShell (with Docker Desktop running):
     .\scripts\docker-build-and-up.ps1
#>

Write-Host "Checking Docker availability..."
docker version > $null 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Error "Docker doesn't seem to be running or not installed. Start Docker Desktop and try again."
    exit 1
}

Write-Host "Building images (this will run maven inside builder stages)..."
if (Get-Command 'docker' -ErrorAction SilentlyContinue) {
    docker compose build --pull
    if ($LASTEXITCODE -ne 0) { Write-Error "Build failed"; exit 1 }
    Write-Host "Starting containers (detached)..."
    docker compose up -d
    if ($LASTEXITCODE -ne 0) { Write-Error "docker compose up failed"; exit 1 }
} else {
    Write-Error "docker CLI not found"
    exit 1
}

Write-Host "Stack started. Use 'docker compose ps' to see running services."
Write-Host "Logs: docker compose logs -f usuario|core|carrito"
