FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}

RUN echo ${SPRING_PROFILES_ACTIVE}

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/store-api-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]