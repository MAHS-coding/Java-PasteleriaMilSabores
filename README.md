# 🍰 Pastelería Mil Sabores - Backend

## 📌 Descripción  
Este proyecto corresponde al **backend** de la aplicación **Pastelería Mil Sabores**, desarrollado en el marco de la asignatura **Java: Diseño y Construcción de Soluciones Nativas en Nube** de Duoc UC.  

El sistema implementa la lógica de negocio y expone servicios RESTful para gestionar la información de usuarios, productos, pedidos y promociones de la pastelería. Además, integra reglas de negocio especiales, como descuentos para mayores de 50 años, promociones con código `FELICES50`, y tortas gratis para estudiantes de Duoc en su cumpleaños.  

## 🎯 Objetivos del Proyecto
- Diseñar un backend escalable y mantenible con **Java y Spring Boot**.  
- Implementar **arquitectura de microservicios** orientada a la nube.  
- Gestionar catálogo de productos, usuarios y pedidos en línea.  
- Facilitar la integración con un frontend web responsivo.  
- Preparar el sistema para **despliegue en entornos cloud**.  

## 🛠️ Tecnologías Utilizadas
- **Java 17+**  
- **Spring Boot**  
- **Maven** (gestión de dependencias)  
- **MySQL / PostgreSQL** (base de datos relacional)  
- **Docker** (contenedorización)  
- **Git & GitHub** (control de versiones)  
- **CI/CD** (automatización de despliegues)  

## ⚙️ Requerimientos Funcionales Principales
- Registro y autenticación de usuarios con reglas de negocio de descuentos.  
- Gestión de perfiles de usuario y preferencias de compra.  
- Catálogo de productos con filtros (categorías, tamaños, personalización).  
- Carrito de compras y resumen detallado.  
- Procesamiento y seguimiento de pedidos con estados.  
- Gestión de envíos y selección de fechas de entrega.  

## 🚀 Instalación y Ejecución

1. **Clonar el repositorio**  
   ```bash
   git clone https://github.com/tu-usuario/pasteleria-mil-sabores-backend.git
   cd pasteleria-mil-sabores-backend
Construir el proyecto con Maven

bash
Copiar código
mvn clean install
Levantar el servidor local

bash
Copiar código
mvn spring-boot:run
El backend estará disponible en:

arduino
Copiar código
http://localhost:8080
📡 Endpoints Principales (ejemplo)
POST /api/auth/register → Registrar usuario

POST /api/auth/login → Autenticar usuario

GET /api/productos → Listar catálogo de productos

POST /api/pedidos → Crear pedido

GET /api/pedidos/{id} → Consultar estado de un pedido

📂 Estructura del Proyecto
bash
Copiar código
src/
 ├── main/java/com/pasteleria/milsabores
 │    ├── controller   # Controladores REST
 │    ├── model        # Entidades del dominio
 │    ├── repository   # Repositorios JPA
 │    ├── service      # Lógica de negocio
 │    └── PasteleriaApplication.java
 ├── main/resources
 │    ├── application.properties
 │    └── static
👨‍💻 Autores
Este proyecto fue desarrollado por:

Matías Heyer

Gustavo Alvial

Como parte de la asignatura Java: Diseño y Construcción de Soluciones Nativas en Nube de Duoc UC.
