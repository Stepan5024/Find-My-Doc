# Используем официальный образ OpenJDK 11 в качестве базового
FROM openjdk:22-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл JAR в контейнер
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Экспонируем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
