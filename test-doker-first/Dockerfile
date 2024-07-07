FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /build/libs/test-doker-first-1.0-SNAPSHOT.jar /app/test-doker.jar
ENTRYPOINT ["java", "-jar", "test-doker.jar"]
EXPOSE 8002