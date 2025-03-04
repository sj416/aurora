#!/bin/bash

# 전달된 환경 변수 사용
AWS_REGION="ap-northeast-2"
CLUSTER_NAME=$ECS_CLUSTER
SERVICE_NAME=$ECS_SERVICE
TASK_DEFINITION=$ECS_TASK_DEFINITION

# AWS CLI를 사용하여 ECS 서비스를 업데이트
echo "Updating ECS service in cluster $CLUSTER_NAME with task definition $TASK_DEFINITION..."
aws ecs update-service \
  --cluster $CLUSTER_NAME \
  --service $SERVICE_NAME \
  --task-definition $TASK_DEFINITION \
  --desired-count 1 \
  --region $AWS_REGION

echo "ECS service update complete!"
