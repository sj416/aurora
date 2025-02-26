# OpenJDK 기반의 이미지 사용
FROM openjdk:17-jdk-slim

# 필수 패키지 설치 (curl, dpkg, apt-utils 등)
RUN apt-get update && \
    apt-get install -y curl dpkg apt-utils && \
    apt-get clean

# CloudWatch Agent 다운로드 및 설치
RUN curl -O https://s3.amazonaws.com/amazoncloudwatch-agent/linux/amd64/latest/AmazonCloudWatchAgent.deb && \
    ls -l AmazonCloudWatchAgent.deb && \
    dpkg -i AmazonCloudWatchAgent.deb || (apt-get update && apt-get install -y -f && dpkg -i AmazonCloudWatchAgent.deb) && \
    rm -f AmazonCloudWatchAgent.deb

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# CloudWatch Agent 설정 파일 복사
COPY amazon-cloudwatch-agent.json /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json

# 컨테이너 실행 시 애플리케이션 실행
CMD amazon-cloudwatch-agent-ctl -a fetch-config \
        -c file:/opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json \
        -s && \
    java -jar app.jar

# 컨테이너에서 접근할 포트
EXPOSE 8080
