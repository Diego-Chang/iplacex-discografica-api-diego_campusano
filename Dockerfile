# Primera Etapa.
FROM gradle:jdk21 AS builder
WORKDIR /app
COPY ./build.gradle .
COPY ./settings.gradle .
COPY src ./src
RUN gradle build clean -x test --no-daemon

# Segunda Etapa.
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar discografia.jar
EXPOSE 443
CMD ["java", "-jar", "discografia.jar"]