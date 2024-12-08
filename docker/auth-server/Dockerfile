# filepath: C:/Users/bokar/Documents/GitHub/Find-My-Doc/docker/auth-service/Dockerfile

FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app/backend

# Копирование родительского POM
COPY backend/pom.xml .
COPY backend/common/pom.xml common/pom.xml

# Копирование POM и исходного кода auth-server
COPY backend/auth-server/pom.xml auth-server/pom.xml
COPY backend/auth-server/src auth-server/src

# Copy api-gateway module
COPY backend/api-gateway/pom.xml api-gateway/pom.xml
COPY backend/api-gateway/src api-gateway/src

# Сборка проекта auth-server и его зависимостей
RUN mvn clean package -pl auth-server -am -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app/backend

# Копирование собранного JAR файла
COPY --from=build /app/backend/auth-server/target/*.jar auth-server.jar

ENTRYPOINT ["java", "-jar", "auth-server.jar"]