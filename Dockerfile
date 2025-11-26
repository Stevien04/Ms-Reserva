# ========= ETAPA 1: COMPILAR CON MAVEN =========
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


# ========= ETAPA 2: EJECUTAR LA APP =========
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render NO usa este puerto, pero lo dejamos descriptivo
EXPOSE 8084

# ARRANCAR USANDO EL PUERTO DE RENDER ($PORT)
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
