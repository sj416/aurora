#!/bin/bash

CLUSTER_NAME=${ECS_CLUSTER}
SERVICE_NAME=${ECS_SERVICE}
TASK_DEFINITION=${ECS_TASK_DEFINITION}

# 새로운 Task Definition 등록
NEW_TASK_DEF_ARN=$(aws ecs register-task-definition \
  --family $TASK_DEFINITION \
  --container-definitions file://container-definition.json \
  --query 'taskDefinition.taskDefinitionArn' \
  --output text)

# 서비스 업데이트
aws ecs update-service \
  --cluster $CLUSTER_NAME \
  --service $SERVICE_NAME \
  --task-definition $NEW_TASK_DEF_ARN \
  --desired-count 1

echo "Service update started successfully."
