#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as maven-builder
COPY . /usr/app
COPY pom.xml /usr/app

RUN mvn -f /usr/app/pom.xml clean package -DskipTests
RUN ["ls", "./usr/app/"]
#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

COPY /usr/app/target/*.jar /usr/app.jar

EXPOSE 8080

ENTRYPOINT java -jar /usr/app.jar