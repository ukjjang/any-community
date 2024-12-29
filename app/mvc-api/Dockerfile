FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar mvc-api.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "mvc-api.jar"]
