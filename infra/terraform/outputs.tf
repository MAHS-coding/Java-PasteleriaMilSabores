output "ecr_repositories" {
  description = "Map of created ECR repository URIs"
  value = { for k, r in aws_ecr_repository.repos : k => r.repository_url }
}

output "sqs_queue_url" {
  description = "SQS queue URL"
  value       = aws_sqs_queue.milsabores.id
}

output "sqs_queue_arn" {
  description = "SQS queue ARN"
  value       = aws_sqs_queue.milsabores.arn
}

output "lambda_role_arn" {
  description = "IAM role ARN for Lambda execution"
  value       = aws_iam_role.lambda_exec.arn
}

output "lambda_function_arn" {
  description = "ARN of created Lambda (if created)"
  value       = try(aws_lambda_function.process_sqs[0].arn, "")
}
