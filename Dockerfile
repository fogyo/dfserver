# Используем базовый образ с Java Runtime Environment
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл JAR в контейнер
COPY target/DFGServer-0.0.1-SNAPSHOT.jar /app/DFGServer-0.0.1-SNAPSHOT.jar

# Указываем команду для запуска сервера
CMD ["java", "-jar", "/app/DFGServer-0.0.1-SNAPSHOT.jar"]
