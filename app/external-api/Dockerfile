FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar external-api.jar

ENTRYPOINT ["java", "-jar", "external-api.jar"]
