FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar consumer.jar

ENTRYPOINT ["java", "-jar", "consumer.jar"]
