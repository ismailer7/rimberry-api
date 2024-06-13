#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as maven-builder
COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package -DskipTests
RUN "ls"
#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
RUN "ls"
ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

COPY ${HOME}/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar /app.jar