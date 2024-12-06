FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/reward-service-0.0.1-SNAPSHOT.jar /app/reward-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/reward-service-0.0.1-SNAPSHOT.jar"]
