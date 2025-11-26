<#
Scale services in the deployed stack.
Usage:
  .\scale-services.ps1 -ServiceName usuario -Replicas 4
#>

param(
  [Parameter(Mandatory=$true)][string]$ServiceName,
  [Parameter(Mandatory=$true)][int]$Replicas,
  [string]$StackName = 'milsabores'
)

Write-Host "Scaling service $ServiceName to $Replicas replicas in stack $StackName"
docker service scale ${StackName}_$ServiceName=$Replicas
if ($LASTEXITCODE -ne 0) { Write-Error "Scaling failed"; exit 1 }
Write-Host "Scaled. Check status with: docker service ls | grep $ServiceName"
