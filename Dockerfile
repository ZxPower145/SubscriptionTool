FROM openjdk:22-slim
COPY target/your-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]