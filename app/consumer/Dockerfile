FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar consumer.jar

ENTRYPOINT ["java", "-XX:InitialRAMPercentage=75.0", "-XX:MaxRAMPercentage=75.0", "-jar", "consumer.jar"]
