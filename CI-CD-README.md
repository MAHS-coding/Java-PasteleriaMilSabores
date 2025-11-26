**CI/CD and Deployment**

This document explains the GitHub Actions workflow added to build, push and optionally deploy the microservices.

- Workflow file: `.github/workflows/ci-cd.yml`
- Registry: GHCR (GitHub Container Registry) by default. You can change tags in the workflow.

Setup steps
1. In GitHub repository settings, under Secrets, add the following (if you want to push to GHCR and deploy):
   - `GITHUB_TOKEN` (automatically provided by Actions) — ensure `packages: write` permission in workflow `permissions` section.
   - `SSH_PRIVATE_KEY` — private key for the remote deploy user.
   - `REMOTE_HOST` — IP or DNS of remote VM.
   - `REMOTE_USER` — username on remote VM.

How the workflow works
1. `build` job: uses a matrix to build each microservice using the included `mvnw` wrapper and uploads the produced JAR as artifact.
2. `docker_build_and_push` job: logs into GHCR and builds/pushes Docker images (tags: `ghcr.io/<OWNER>/usuario:latest`, etc.).
3. `deploy` job (optional): if triggered via `workflow_dispatch` with `deploy=true`, uses SSH private key to execute a remote `docker stack deploy` that pulls the pushed images and deploys the stack.

Local testing and fast iteration
- For faster local builds during development, run `mvnw package` in each service and build images using the local JAR instead of rebuilding with Maven inside Docker. See Dockerfiles for `ARG JAR_FILE`.
- Use `scripts/docker-build-and-up.ps1` to build images and bring the stack up locally.

Notes
- This workflow assumes the remote host is already a Docker Swarm manager and has the `docker-compose.yml` placed in `/home/<user>/project/docker-compose.yml` or adjust `scripts/swarm-deploy.ps1`.
- For production deployments consider using a container registry with proper access controls and a secure deployment pipeline.
