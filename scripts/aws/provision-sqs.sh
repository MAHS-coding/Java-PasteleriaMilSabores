#!/usr/bin/env bash
# Simple provisioning script to create an SQS queue. Intended to be called from CI.
set -euo pipefail

QUEUE_NAME=${1:-milsabores-queue}

echo "Provisioning SQS queue: $QUEUE_NAME"
if aws sqs list-queues --query "QueueUrls[]" --output text | grep -q "$QUEUE_NAME"; then
  echo "Queue seems to already exist (partial match). Skipping creation."
else
  aws sqs create-queue --queue-name "$QUEUE_NAME"
  echo "Queue created: $QUEUE_NAME"
fi

echo "Done."
