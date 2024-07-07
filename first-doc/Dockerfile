#Базовый образ
FROM openjdk:17-jdk-alpine
LABEL authors="tttti"

#Открыть порт на котором работает приложении
EXPOSE 8080

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл приложения в рабочую директорию
COPY build/libs/first-doc-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "myapp.jar"]
