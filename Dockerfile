# Use a imagem base do OpenJDK
FROM openjdk:22-jdk-bullseye

# Instale o Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e outros arquivos necessários para baixar as dependências
COPY pom.xml .

# Baixar as dependências do Maven (isso ajuda a cachear as dependências)
RUN mvn dependency:go-offline -B

# Copie todos os arquivos do projeto para o diretório de trabalho
COPY . .

# Execute o comando Maven para construir o projeto
RUN mvn clean package -DskipTests

# Verifique se o arquivo JAR existe no diretório target
RUN echo "Verificando arquivos no diretório target:" && ls target/

# Copie o arquivo JAR para o diretório de trabalho e verifique a cópia
RUN cp target/*.jar app.jar && \
    echo "Verificando se app.jar está presente:" && ls -l app.jar

# Defina o ponto de entrada para executar o arquivo JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
