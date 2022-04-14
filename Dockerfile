FROM java:8-jdk

COPY . .

CMD ./mvnw package

ENTRYPOINT java -jar target/blps.jar