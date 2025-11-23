# Primera Etapa.
FROM gradle:jdk21 AS builder
WORKDIR /app
COPY ./build.gradle .
COPY ./settings.gradle .
COPY src ./src
RUN gradle clean build -x --no-daemon

# Segunda Etapa.
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar discografia-1.jar
EXPOSE 443
CMD ["java", "-jar", "discografia-1.jar"]