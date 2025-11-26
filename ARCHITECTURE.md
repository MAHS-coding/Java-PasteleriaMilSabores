**Architecture decisions and rationale**

Overview
--------
This project is organized as three Java Spring Boot microservices: `usuario`, `core`, and `carrito`. The deployment model for the evaluation uses Docker containers orchestrated with Docker Swarm and optional cloud resources (ECR, SQS, Lambda) provisioned through Terraform.

Key decisions
-------------
- Containerization: multi-stage Dockerfiles build the JAR with Maven in a builder stage and copy the artifact into a smaller runtime image. This keeps images reproducible and safe from local build environment differences.
- Registry: GitHub Container Registry (GHCR) is used in CI as a default for convenience and simplicity within GitHub Actions. The workflow can be adjusted to push to ECR or Docker Hub.
- Orchestration: Docker Swarm chosen for the course requirement (IE7). Swarm provides simple service deployment (`docker stack deploy`) and scaling (`docker service scale`) for this evaluation.
- CI/CD: GitHub Actions workflows build Java artifacts, container images, and optionally deploy to a remote host via SSH. This provides reproducible builds and an automated delivery path (IE4).
- Infrastructure as Code: Terraform modules in `infra/terraform` manage cloud resources (ECR repositories, SQS queue, Lambda role/function). Using Terraform allows repeatable provisioning (IE5, IE11, IE14).
- Serverless: a Lambda (optional) can be created and wired to SQS for asynchronous processing (IE10, IE13). The code under `lambda/process-sqs` should be packaged and uploaded to S3, then Terraform can create the Lambda and event mapping.

Scalability, availability and maintainability
-------------------------------------------
- Scalability: services declare `deploy.replicas` in `docker-compose.yml` and can be scaled with `docker service scale`. Images are stateless; session/state should be stored in DB or external caches for horizontal scaling.
- Availability: Swarm supports scheduling replicas across nodes; healthchecks (added to Dockerfiles) allow orchestrator to detect unhealthy containers and restart them.
- Maintainability: CI produces images with clear tags and metadata. Terraform centralizes infra configuration. Using documented scripts and workflows reduces manual steps.

Security notes
--------------
- Secrets: do not store `jwt.secret` or DB passwords in the repository. Use `.env` for local dev (gitignored) and GitHub Secrets for CI. For cloud, use a secret manager.
- Network: in production, restrict access to management ports and use VPN or bastion hosts for SSH access to deploy nodes.

How this meets the rubric
------------------------
- IE1: Dockerfiles are multi-stage and include healthchecks and labels.
- IE2/IE3/IE7/IE8: Compose files + Swarm-ready `deploy` keys + scripts allow local container generation, Swarm init, join, scale and stack deployment.
- IE4/IE5/IE6: CI workflow builds/pushes images and can deploy to a remote host using SSH; Terraform workflow provisions cloud resources.
- IE10/IE11/IE13/IE14: Terraform provides ECR, SQS and optional Lambda resources; glue code needed to package and upload Lambda JAR for full automation.
