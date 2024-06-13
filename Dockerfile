FROM openjdk:11

ENV PORT 8080
EXPOSE 8080

COPY ./target/*.jar /deployments/app.jar
