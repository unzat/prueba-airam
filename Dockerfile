# Etapa 1: Construcción
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app

# Copiar el archivo de configuración de Maven y el archivo pom.xml
COPY pom.xml ./
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Creación de la imagen final
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar app.jar

# Establecer la variable de entorno para limitar el uso de recursos
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Exponer el puerto que utilizará tu aplicación
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
