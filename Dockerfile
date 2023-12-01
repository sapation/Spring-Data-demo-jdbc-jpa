FROM openjdk:17-jdk-alpine
MAINTAINER webtect.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]