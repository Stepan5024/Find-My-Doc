# filepath: C:/Users/bokar/Documents/GitHub/Find-My-Doc/docker/api-gateway/Dockerfile
# ------------------ Сборка фронтенда ---------------------
FROM node:18 AS frontend
WORKDIR /app/frontend

# Копируем package.json и package-lock.json
COPY frontend/package*.json ./

# Копируем оставшиеся файлы фронтенда
COPY frontend/ .

# Устанавливаем зависимости
RUN npm install

# Сборка фронтенда
RUN npm run build
# ------------------ Сборка бэкенда -----------------------

FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app/backend

# Copy parent and common
COPY backend/pom.xml .
COPY backend/common/pom.xml common/pom.xml

# Copy api-gateway module
COPY backend/api-gateway/pom.xml api-gateway/pom.xml
COPY backend/api-gateway/src api-gateway/src

# Copy auth-server module too (since parent POM expects it)
COPY backend/auth-server/pom.xml auth-server/pom.xml
COPY backend/auth-server/src auth-server/src

# Копируем собранный фронтенд в статические ресурсы API Gateway
COPY --from=frontend /app/frontend/build/ api-gateway/src/main/resources/static/

# Копируем исходный код фронтенда для Maven
COPY frontend/ /app/frontend/

# Запускаем сборку только нужного модуля вместе с зависимыми
RUN mvn clean package -pl api-gateway -am -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app/backend

# Копирование собранного JAR файла
COPY --from=build /app/backend/api-gateway/target/*.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]