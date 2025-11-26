# Usamos una imagen base ligera de Java 17
FROM openjdk:17-jdk-slim

# Añadimos un volumen temporal
VOLUME /tmp

# Copiamos el archivo JAR generado por Maven
COPY target/*.jar app.jar

# Ejecutamos la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]