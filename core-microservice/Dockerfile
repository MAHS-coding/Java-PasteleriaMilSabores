# Multi-stage Dockerfile for core-microservice
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml mvnw .mvn/ ./
COPY src ./src
RUN mvn -B -DskipTests package -DskipTests

FROM eclipse-temurin:17-jre-jammy
LABEL maintainer="MAHS-coding <you@example.com>"
ARG JAR_FILE=target/core-microservice-0.0.1-SNAPSHOT.jar
ARG IMAGE_VERSION=latest
RUN useradd --system --create-home appuser
WORKDIR /app
COPY --from=builder /workspace/${JAR_FILE} app.jar
RUN chown appuser:appuser app.jar
USER appuser
ENV JAVA_OPTS="-Xms128m -Xmx512m"
EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 CMD curl -f http://localhost:8081/actuator/health || exit 1
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
