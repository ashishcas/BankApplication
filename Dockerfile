FROM openjdk:8-jdk-alpine
LABEL maintainer="chiluka.ashish@gmail.com"
VOLUME /main-app
COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
# EXPOSE 8080
