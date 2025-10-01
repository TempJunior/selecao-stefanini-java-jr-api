# --------- STAGE 1: BUILD ---------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia os arquivos do projeto e realiza o build (sem testes)
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests clean package

# --------- STAGE 2: RUNTIME ---------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR final do build para a imagem final
# Nome baseado em artifactId e version do pom.xml: api-autores-obras-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/api-autores-obras-0.0.1-SNAPSHOT.jar /app/app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]