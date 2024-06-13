#
# Build stage
#
FROM maven:3.8.4-openjdk-11 as maven-builder
COPY . /usr/app
COPY pom.xml /usr/app

RUN ["ls", "/usr/app"]
RUN mvn -f /usr/app/pom.xml clean package -DskipTests
#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

EXPOSE 8080
