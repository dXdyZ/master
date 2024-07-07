FROM openjdk:17-jdk-alpine
LABEL authors="tttti"

EXPOSE 8080

WORKDIR /app

COPY build/libs/new-taco-0.0.1-SNAPSHOT.jar /app/taco.jar

CMD ["java", "-jar", "taco.jar"]