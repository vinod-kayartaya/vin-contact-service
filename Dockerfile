FROM adoptopenjdk/openjdk11:alpine
WORKDIR /vinod/apps
COPY ./target/contact-service-0.0.1-SNAPSHOT.jar ./app.jar
COPY ./contacts.json ./contacts.json
EXPOSE 8080
ENTRYPOINT java -jar ./app.jar