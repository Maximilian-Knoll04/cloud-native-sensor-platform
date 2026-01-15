FROM maven:3.9.11-eclipse-temurin-25-alpine
EXPOSE 8080
COPY target/app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java" , "-jar", "/app.jar"]