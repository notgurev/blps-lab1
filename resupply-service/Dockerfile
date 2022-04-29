FROM maven:3.5-jdk-8 AS build

COPY pom.xml .
RUN mvn verify --fail-never

COPY src src
RUN mvn package

FROM openjdk:8-jre-slim

COPY --from=build target/app.jar app.jar

EXPOSE 8081

ENTRYPOINT java -jar app.jar