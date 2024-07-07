#На основе какого image будет запущен
FROM openjdk:17-jdk-slim-buster
#Создаем папку на ос
WORKDIR /app
#Копируем исполняемый файл
COPY /build/libs/tasc-man-0.0.1-SNAPSHOT.jar /app/task-man.jar
#Выполняемая команда для запуска файла
ENTRYPOINT ["java", "-jar", "task-man.jar"]