#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as maven-builder
COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
RUN "ls"
ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/runner.jar