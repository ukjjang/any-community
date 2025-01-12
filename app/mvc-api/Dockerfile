FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar mvc-api.jar

ENTRYPOINT ["java", "-XX:InitialRAMPercentage=75.0", "-XX:MaxRAMPercentage=75.0", "-jar", "mvc-api.jar"]
