# ========= ETAPA 1: COMPILAR CON MAVEN =========
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del proyecto
COPY src ./src

# Compilar sin tests
RUN mvn clean package -DskipTests


# ========= ETAPA 2: EJECUTAR LA APP =========
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar JAR de la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto 8084 (tu backend)
EXPOSE 8084

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
