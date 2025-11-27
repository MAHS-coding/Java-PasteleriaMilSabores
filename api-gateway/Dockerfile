# Etapa de build: compila el JAR usando Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copiamos pom y código fuente y empaquetamos (sin tests para velocidad)
COPY pom.xml ./
COPY src ./src

RUN mvn -B clean package -DskipTests


# Etapa de ejecución: imagen final ligera
FROM eclipse-temurin:17-jdk-alpine

# Añadimos un volumen temporal
VOLUME /tmp

# Copiamos el JAR generado en la etapa de build
COPY --from=build /workspace/target/*.jar /app.jar

# Ejecutamos la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]