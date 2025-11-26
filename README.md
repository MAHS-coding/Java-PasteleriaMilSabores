Proyecto backend para Pastelería mil Sabores
Este es el proyecto backend para la pastelería Mil Sabores, una tienda online para la venta de pasteles y repostería en general. Aquí se explica paso a paso cómo levantar y revisar cada microservicio.

Equipo 12:

Gustavo Alvial
Matías Heyer

Este proyecto contiene tres microservicios independientes para gestionar usuarios, productos y carritos de compra.

Microservicios incluidos
1. usuarios-microservice
Permite registrar, consultar, actualizar y eliminar usuarios.
Puerto por defecto: 8080
Base de datos: bd_usuarios_pasteleria

2. core-microservice
Permite registrar, consultar, actualizar y eliminar productos y categorías.
Puerto por defecto: 8081
Base de datos: bd_core_pasteleria

3. carrito-microservice
Permite crear, consultar, actualizar y eliminar carritos de compra asociados a los usuarios.
Puerto por defecto: 8082
Base de datos: bd_carrito_pasteleria

Tecnologías utilizadas
Java 17
Spring Boot 3.1.5
Spring Data JPA
MySQL 8.0
Maven
Lombok
Scripts de prueba
Cada microservicio incluye un script SQL con datos de prueba en su carpeta src/main/resources.

Como parte de la asignatura Java: Diseño y Construcción de Soluciones Nativas en Nube de Duoc UC.

## CI/CD (GitHub Actions)

Hay un workflow inicial en `.github/workflows/ci-cd.yml` que realiza:


Secrets de GitHub requeridos para la etapa de deploy:


## CI/CD: ECR, Lambda and extended deploy

An extended workflow is provided at `.github/workflows/ci-cd-ecr-lambda.yml`. It builds the Java modules, builds Docker images for each microservice and pushes them to Amazon ECR, packages the Lambda fat JAR and uploads it to S3, then provisions basic resources (ECR repositories and an SQS queue) and creates/updates a Lambda function from the uploaded jar.

Additional GitHub Secrets required for the extended workflow:

- `AWS_ACCOUNT_ID` — your AWS account id (used to tag images pushed to ECR)
- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_REGION` (e.g. us-east-1)
- `ECR_PREFIX` — an ECR repository name prefix used as `<prefix>/carrito`, `<prefix>/core`, `<prefix>/usuario`
- `S3_BUCKET` — bucket where the Lambda jar will be uploaded
- `LAMBDA_EXEC_ROLE` — IAM role ARN that the Lambda function will use (must exist and have lambda execution + SQS permissions)

Note: the workflow is a starting point — you must create the IAM role, grant the runner permissions to create ECR repos, and set the Secrets in the repository settings before the deploy job will run successfully.

## Troubleshooting

- If you see a Java package vs file path mismatch when building the Lambda module, verify that the handler source file sits at `lambda/process-sqs/src/main/java/milsabores/lambda/ProcessSqsHandler.java` and that the first line in the file is `package milsabores.lambda;` (case-sensitive). Maven expects the directory structure to match the package declaration.
- If `mvn` is not available locally, use each microservice's Maven wrapper (e.g. `carrito-microservice\mvnw.cmd` on Windows) or install Maven system-wide.

### Build the Lambda locally (Windows)

There's a helper PowerShell script at `scripts\build-lambda.ps1` that will use the existing Maven wrapper present in `core-microservice` to build the `lambda/process-sqs` module and optionally upload the resulting jar to S3.

Example (PowerShell):

```powershell
# Build only
.\scripts\build-lambda.ps1

# Build and upload to S3 (requires AWS CLI configured / credentials available)
.\scripts\build-lambda.ps1 -UploadToS3 -S3Bucket your-bucket-name
```

This is useful when you don't have a `mvn` installation system-wide. The script calls the `mvnw.cmd` wrapper shipped in `core-microservice` which downloads Maven automatically the first time it runs.

### Quick Docker Swarm deploy (local)

If you want to quickly deploy the three services to a local Docker Swarm (for demo), set the `REGISTRY` env var to your image registry (or leave unset to build locally) and run:

```powershell
# initialize swarm (if not already a manager)
docker swarm init

# build and deploy stack (will build images from the Dockerfiles)
docker stack deploy -c docker-compose.yml milsabores

# check services
docker stack services milsabores
```

Notes:
- To push images to ECR you should set `REGISTRY` to your ECR repo prefix (for example `123456789012.dkr.ecr.us-east-1.amazonaws.com/milsabores`) and make sure images are already pushed to that registry. The GitHub Actions workflow can build and push images to ECR for you when Secrets are configured.

El workflow está pensado como punto de partida. Próximos pasos recomendados:

1. Añadir Dockerfiles por microservicio (multi-stage).  
2. Extender el workflow para construir imágenes Docker y subirlas a AWS ECR.  
3. Añadir IaC (Terraform / CloudFormation) para aprovisionar ECS/Fargate, RDS, SQS, IAM roles.

Hay un script helper `scripts/aws/provision-sqs.sh` para crear la cola SQS desde CI o localmente (requiere AWS CLI configurado si se ejecuta localmente).
