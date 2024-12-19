# Build stage
FROM maven:3.9.9 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean package -Dmaven.test.skip=true

# Run stage
FROM openjdk:17.0.2-slim as run
COPY --from=build /app/target/file-management-service.jar /file-management-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/file-management-service.jar"]
