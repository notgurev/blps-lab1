FROM maven:3.5-jdk-8 AS build

COPY pom.xml .
RUN mvn dependency:resolve

COPY src src
RUN mvn package

# я хз почему тут зависимости скачиваются каждый раз, не получается нормально сделать :(

FROM openjdk:8-jre-slim
COPY --from=build target/blps.jar blps.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","blps.jar"]