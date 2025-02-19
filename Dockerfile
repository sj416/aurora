# OpenJDK 기반의 이미지 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# 컨테이너 실행 시 실행할 명령어
CMD ["java", "-jar", "app.jar"]

# 컨테이너에서 접근할 포트
EXPOSE 8080
