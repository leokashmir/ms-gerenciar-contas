# Use a imagem base do OpenJDK 22
FROM openjdk:22-jdk-bullseye
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /opt/app
COPY . .
RUN mvn clean package -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
