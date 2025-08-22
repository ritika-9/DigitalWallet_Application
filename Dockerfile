# Use Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Make Maven wrapper executable & build
RUN chmod +x ./mvnw
RUN ./mvnw -DskipTests clean package

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/wallet-0.0.1-SNAPSHOT.jar"]

