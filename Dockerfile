FROM ubuntu:latest

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . /app

RUN apt-get install maven -y
RUN cd app && mvn clean install

EXPOSE 8080


ENTRYPOINT [ "java", "-jar", "/app/app.jar"]