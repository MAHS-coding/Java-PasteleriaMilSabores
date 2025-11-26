<#
Initializes a Docker Swarm manager on the current host and prints the join command for workers.
Run on the host you want to be the Swarm manager.
#>

Write-Host "Initializing Docker Swarm manager..."
docker swarm init --advertise-addr $(hostname -I | awk '{print $1}')
if ($LASTEXITCODE -ne 0) { Write-Error "Failed to init swarm"; exit 1 }

Write-Host "Worker join command (run on worker nodes):"
docker swarm join-token worker -q | ForEach-Object { Write-Host "docker swarm join --token $_ <MANAGER_IP>:2377" }
