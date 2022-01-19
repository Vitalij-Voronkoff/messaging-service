FROM adoptopenjdk/openjdk11:alpine

RUN mkdir /source
WORKDIR /source
COPY . /source

RUN ./mvnw clean package

ENTRYPOINT ["java","-jar","target/messaging-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080