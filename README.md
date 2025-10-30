Proyecto backend para Pastelería mil Sabores
Este es el proyecto backend para la pastelería Mil Sabores, una tienda online para la venta de pasteles y repostería en general. Aquí se explica paso a paso cómo levantar y revisar cada microservicio.

Equipo 12:

Gustavo Alvial
Matías Heyer

Este proyecto contiene tres microservicios independientes para gestionar usuarios, productos y carritos de compra.

Microservicios incluidos
1. usuarios-microservice
Permite registrar, consultar, actualizar y eliminar usuarios.
Puerto por defecto: 8080
Base de datos: bd_usuarios_pasteleria

2. core-microservice
Permite registrar, consultar, actualizar y eliminar productos y categorías.
Puerto por defecto: 8081
Base de datos: bd_core_pasteleria

3. carrito-microservice
Permite crear, consultar, actualizar y eliminar carritos de compra asociados a los usuarios.
Puerto por defecto: 8082
Base de datos: bd_carrito_pasteleria

Tecnologías utilizadas
Java 17
Spring Boot 3.1.5
Spring Data JPA
MySQL 8.0
Maven
Lombok
Scripts de prueba
Cada microservicio incluye un script SQL con datos de prueba en su carpeta src/main/resources.

Como parte de la asignatura Java: Diseño y Construcción de Soluciones Nativas en Nube de Duoc UC.

Guía para compilar y ejecutar:
1. Clonar el repositorio
Utilizando la terminal, se puede clonar el repositorio con el comando git clone https://github.com/MAHS-coding/Java-PasteleriaMilSabores.git.

2. Crear las bases de datos
En el cliente MySQL que esté utilizando, utilizar el script
CREATE DATABASE bd_usuarios_pasteleria;
CREATE DATABASE bd_core_pasteleria;
CREATE DATABASE bd_carrito_pasteleria;

3. Configurar la conexión de los microservicios
En el archivo application.properties de cada microservicio, incluir tus datos de MySQL. Se puede cambiar el puerto en caso de que esté ocupado.

4. Compilar y ejecutar un microservicio
Dentro de cada microservicio se encuentra un archivo con la nomenclatura Application. Al ejecutar ese archivo, se pondrá en marcha el microservicio, y la conexión a su base de datos(definida en el application.properties).

5. Ejecutar pruebas de bd y endpoints
Una vez ejecutado el microservicio, ya es posible ejecutar los scripts de los archivos .sql y también se pueden testear los endpoints indicados en el controller de cada microservicio.
