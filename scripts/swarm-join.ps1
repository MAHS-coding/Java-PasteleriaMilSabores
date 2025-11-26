<#
Join this host to an existing swarm as a worker.
Usage:
  .\swarm-join.ps1 -ManagerIp '1.2.3.4' -Token 'SWMTKN-1-...'
#>

param(
    [Parameter(Mandatory=$true)] [string]$ManagerIp,
    [Parameter(Mandatory=$true)] [string]$Token
)

Write-Host "Joining swarm manager $ManagerIp as worker..."
docker swarm join --token $Token $ManagerIp:2377
if ($LASTEXITCODE -ne 0) { Write-Error "Failed to join swarm"; exit 1 }
Write-Host "Joined as worker. Use 'docker node ls' on manager to verify."
