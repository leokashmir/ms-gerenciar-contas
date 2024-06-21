# Use a imagem base do OpenJDK 22
FROM openjdk:22-jdk-bullseye
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]