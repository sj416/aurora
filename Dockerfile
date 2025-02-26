# OpenJDK 기반의 이미지 사용
FROM openjdk:17-jdk-slim

# 필수 패키지 설치 (curl, apt-utils 등)
RUN apt-get update && \
    apt-get install -y curl apt-utils && \
    apt-get clean

# CloudWatch Agent 다운로드 및 설치
RUN curl -O https://amazoncloudwatch-agent.s3.amazonaws.com/ubuntu/amd64/latest/amazon-cloudwatch-agent.deb && \
    ls -l amazon-cloudwatch-agent.deb && \
    apt install -y ./amazon-cloudwatch-agent.deb && \
    rm -f amazon-cloudwatch-agent.deb

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# CloudWatch Agent 설정 파일 복사
COPY amazon-cloudwatch-agent.json /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json

# CloudWatch Agent 시작 후 애플리케이션 실행
CMD /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent -c /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json && \
    java -jar app.jar

# 컨테이너에서 접근할 포트
EXPOSE 8080
