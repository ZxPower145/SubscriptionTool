# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .env .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY .env .

# Expose the port your app runs on
EXPOSE ${BACKEND_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]