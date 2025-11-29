# ---------- BUILD STAGE ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build jar (skip tests to go faster)
RUN mvn -q -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/order-service-1.0.0.jar app.jar

# Use "docker" profile if you want later
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
