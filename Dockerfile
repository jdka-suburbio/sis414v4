# Fase de compilación
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Da permisos de ejecución al wrapper de gradle y construye el proyecto
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# Fase de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia el archivo .jar generado (el comodín evita fallos si el nombre cambia)
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]