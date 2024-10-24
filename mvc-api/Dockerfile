FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar mvc-api.jar

ENTRYPOINT ["java", "-jar", "mvc-api.jar"]
