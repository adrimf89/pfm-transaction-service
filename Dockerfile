# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Working directory in container
WORKDIR /app

# Copy project files
COPY pom.xml ./
COPY src ./src

# Build application with maven
RUN mvn clean package -DskipTests

# Stage 2: Create image to run the application
FROM eclipse-temurin:21-jdk-alpine

# Working directory in container
WORKDIR /app

# Copy JAR
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]