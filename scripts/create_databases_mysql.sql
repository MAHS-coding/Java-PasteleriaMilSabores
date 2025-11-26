-- Script para crear las bases de datos usadas por los microservicios
-- Ejecutar con: C:\xampp\mysql\bin\mysql.exe -u root -p < scripts/create_databases_mysql.sql

CREATE DATABASE IF NOT EXISTS bd_usuarios_pasteleria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_core_pasteleria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_carrito_pasteleria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Opcional: crear un usuario limitado para las apps (recomendado)
-- Descomenta y ajusta contraseña antes de ejecutar
-- CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'TuPasswordSegura';
-- GRANT ALL PRIVILEGES ON bd_usuarios_pasteleria.* TO 'appuser'@'localhost';
-- GRANT ALL PRIVILEGES ON bd_core_pasteleria.* TO 'appuser'@'localhost';
-- GRANT ALL PRIVILEGES ON bd_carrito_pasteleria.* TO 'appuser'@'localhost';
-- FLUSH PRIVILEGES;
