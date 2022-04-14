FROM java:8-jdk

COPY . .

CMD ./mvnw package -Pprod

ENTRYPOINT java -jar target/blps.jar