# Primera Etapa.
FROM gradle:jdk21 AS builder
WORKDIR /app
COPY ./build.gradle .
COPY ./settings.gradle .
COPY src ./src
RUN gradle clean build -x test --no-daemon

# Segunda Etapa.
FROM openjdk:26-ea-24-jdk-slim-trixie
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar discografia.jar
EXPOSE 443
CMD ["java", "-jar", "discografia.jar"]
