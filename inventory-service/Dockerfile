# ---------- BUILD STAGE ----------
FROM gradle:8.10-jdk17 AS build
WORKDIR /app

# Copy Gradle setup
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Build fat jar
RUN ./gradlew clean bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar (name will be inventory-service-0.0.1-SNAPSHOT or similar)
COPY --from=build /app/build/libs/inventory-service-*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
