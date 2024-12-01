# Используем базовый образ с Java Runtime Environment
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл JAR в контейнер
COPY target/dfg.jar /app/dfg.jar

# Указываем команду для запуска сервера
CMD ["java", "-jar", "/app/dfg.jar"]
