variable "aws_region" {
  description = "AWS region to deploy resources"
  type        = string
  default     = "us-east-1"
}

variable "ecr_prefix" {
  description = "Prefix for ECR repositories (will create <prefix>-carrito, <prefix>-core, <prefix>-usuario)"
  type        = string
}

variable "sqs_name" {
  description = "Name of the SQS queue to create"
  type        = string
  default     = "milsabores-queue"
}

variable "lambda_create" {
  description = "Whether to create a Lambda function (requires s3_bucket and s3_key to be set)"
  type        = bool
  default     = false
}

variable "lambda_name" {
  description = "Lambda function name (used only if lambda_create = true)"
  type        = string
  default     = "milsabores-process-sqs"
}

variable "s3_bucket_for_lambda" {
  description = "S3 bucket that contains the Lambda JAR (required if lambda_create = true)"
  type        = string
  default     = ""
}

variable "s3_key_for_lambda" {
  description = "S3 key (object) for the Lambda JAR (required if lambda_create = true)"
  type        = string
  default     = ""
}
