terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 4.0"
    }
  }
  required_version = ">= 1.0"
}

provider "aws" {
  region = var.aws_region
}

locals {
  repo_names = ["carrito", "core", "usuario"]
}

# Create ECR repositories
resource "aws_ecr_repository" "repos" {
  for_each = toset(local.repo_names)
  name     = "${var.ecr_prefix}-${each.value}"
  image_tag_mutability = "MUTABLE"
  tags = {
    project = "milsabores"
    service = each.value
  }
}

# Create SQS queue
resource "aws_sqs_queue" "milsabores" {
  name = var.sqs_name
  visibility_timeout_seconds = 30
  message_retention_seconds  = 345600
  tags = {
    project = "milsabores"
  }
}

# IAM role for Lambda (basic execution role)
data "aws_iam_policy_document" "lambda_assume_role" {
  statement {
    effect = "Allow"
    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "lambda_exec" {
  name               = "milsabores-lambda-exec"
  assume_role_policy = data.aws_iam_policy_document.lambda_assume_role.json
  tags = { project = "milsabores" }
}

resource "aws_iam_role_policy" "lambda_exec_policy" {
  name = "milsabores-lambda-policy"
  role = aws_iam_role.lambda_exec.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
        Effect   = "Allow"
        Resource = "arn:aws:logs:*:*:*"
      },
      {
        Action = ["s3:GetObject","s3:GetObjectVersion","s3:HeadObject"]
        Effect = "Allow"
        Resource = "*"
      },
      {
        Action = [
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:ChangeMessageVisibility",
          "sqs:GetQueueAttributes",
          "sqs:GetQueueUrl"
        ]
        Effect = "Allow"
        Resource = aws_sqs_queue.milsabores.arn
      }
    ]
  })
}

# Optional: create Lambda function if requested and s3 bucket/key provided
resource "aws_lambda_function" "process_sqs" {
  count = var.lambda_create ? 1 : 0
  function_name = var.lambda_name
  s3_bucket     = var.s3_bucket_for_lambda
  s3_key        = var.s3_key_for_lambda
  handler       = "milsabores.lambda.ProcessSqsHandler::handleRequest"
  runtime       = "java17"
  role          = aws_iam_role.lambda_exec.arn
  memory_size   = 512
  timeout       = 30
  publish       = true
  tags = { project = "milsabores" }
}

# Optional: create event source mapping from SQS to Lambda if lambda created
resource "aws_lambda_event_source_mapping" "sqs_to_lambda" {
  count = var.lambda_create ? 1 : 0
  event_source_arn = aws_sqs_queue.milsabores.arn
  function_name    = aws_lambda_function.process_sqs[0].arn
  batch_size       = 10
  enabled          = true
}
