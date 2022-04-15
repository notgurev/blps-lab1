FROM maven:3.5-jdk-8 AS build

COPY pom.xml .
RUN mvn verify --fail-never

COPY src src
RUN mvn package

FROM openjdk:8-jre-slim

COPY --from=build target/blps.jar blps.jar

EXPOSE 8080

ENTRYPOINT java -jar blps.jar