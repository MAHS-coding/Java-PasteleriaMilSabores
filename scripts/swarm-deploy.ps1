<#
Deploys the docker stack to a remote host via SSH.
Usage: set environment variables or pass parameters.
Example:
  $env:REMOTE_USER='ubuntu'
  $env:REMOTE_HOST='1.2.3.4'
  $env:REGISTRY='ghcr.io/MAHS-coding'
  .\scripts\swarm-deploy.ps1
#>

param(
    [string]$StackName = 'milsabores',
    [string]$ComposePath = '/home/$env:REMOTE_USER/project/docker-compose.yml'
)

if (-not $env:REMOTE_HOST -or -not $env:REMOTE_USER) {
    Write-Error "Please set REMOTE_HOST and REMOTE_USER environment variables (or edit this script)."
    exit 1
}

Write-Host "Pulling images on remote host and deploying stack..."
ssh -o StrictHostKeyChecking=no $env:REMOTE_USER@$env:REMOTE_HOST "docker pull ${env:REGISTRY:-ghcr.io}/${env:REMOTE_USER}/usuario:latest || true; docker pull ${env:REGISTRY:-ghcr.io}/${env:REMOTE_USER}/core:latest || true; docker pull ${env:REGISTRY:-ghcr.io}/${env:REMOTE_USER}/carrito:latest || true; docker stack deploy -c $ComposePath $StackName"

Write-Host "Deploy command sent. Check remote logs with: ssh $env:REMOTE_USER@$env:REMOTE_HOST 'docker stack ps $StackName'"
