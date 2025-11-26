# Usamos una imagen base ligera y estable de Java 17
# Cambiado a Eclipse Temurin Alpine para compatibilidad y menor tamaño
FROM eclipse-temurin:17-jdk-alpine

# Añadimos un volumen temporal
VOLUME /tmp

# Copiamos el archivo JAR generado por Maven
COPY target/*.jar app.jar

# Ejecutamos la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]