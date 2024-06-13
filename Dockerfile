#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as maven-builder
COPY . /usr/app
COPY pom.xml /usr/app

RUN mvn -f /usr/app/pom.xml clean package -DskipTests
RUN "ls ./usr/app/"
#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
RUN "ls"
ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

COPY ${HOME}/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar /app.jar