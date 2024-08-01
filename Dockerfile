# Usando uma imagem base com JDK 11
FROM openjdk:17-jdk-alpine

# Definindo o diretório de trabalho dentro do contêiner
WORKDIR /app

ARG JAR_FILE=target/*.jar

# Copiando o JAR compilado para o diretório de trabalho
COPY target/gestao_vagas-0.0.1-SNAPSHOT.jar /app/gestao_vagas.jar
COPY src/main/resources/application.properties /app/application.properties

# Expondo a porta em que a aplicação irá rodar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/gestao_vagas.jar"]
