Terraform module to provision minimal AWS resources used by the project

This folder contains a small set of Terraform files that create:
- ECR repositories (one per microservice)
- An SQS queue
- An IAM role for Lambda execution
- Optionally: a Lambda function and an event-source-mapping (SQS->Lambda) if you provide S3 bucket/key with the fat JAR

Pre-requisites
- Terraform 1.0+ installed locally
- AWS credentials configured (environment variables or named profile)

Quick start

1. Copy or export your AWS credentials into environment variables:

```powershell
$env:AWS_ACCESS_KEY_ID = "..."
$env:AWS_SECRET_ACCESS_KEY = "..."
$env:AWS_REGION = "us-east-1"
```

2. Initialize Terraform

```powershell
cd infra\terraform
terraform init
```

3. Plan (example variables)

```powershell
terraform plan -var "ecr_prefix=milsabores" -var "aws_region=us-east-1"
```

4. Apply

```powershell
terraform apply -var "ecr_prefix=milsabores" -var "aws_region=us-east-1" -auto-approve
```

Optional: create Lambda from S3

If you want Terraform to also create the Lambda function, upload `process-sqs-0.0.1.jar` to an S3 bucket and pass these vars:

```powershell
terraform apply -var "ecr_prefix=milsabores" -var "lambda_create=true" -var "s3_bucket_for_lambda=your-bucket" -var "s3_key_for_lambda=process-sqs-0.0.1.jar"
```

Security note
- The module creates an IAM role with basic Lambda execution permissions and grants SQS access to that role. Review and harden the policy before applying in production.
