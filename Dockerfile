FROM openjdk:11

ENV PORT 8080
EXPOSE 8080

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]