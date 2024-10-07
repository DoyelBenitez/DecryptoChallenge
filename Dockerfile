FROM gradle:8.10.2-jdk21-alpine AS builder

WORKDIR /home/gradle/project

COPY build.gradle.kts settings.gradle.kts ./
COPY src src
COPY gradle gradle

RUN gradle build --no-daemon

#JDK 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]