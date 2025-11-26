-- Inserción de datos base
-- Inserción de roles básicos y usuarios según JSON proporcionado
-- Creamos roles que mapean los tipos en el JSON (SUPERADMIN, ADMINISTRADOR, VENDEDOR, CLIENTE)
INSERT INTO roles (nombre) VALUES
('SUPERADMIN'),
('ADMINISTRADOR'),
('VENDEDOR'),
('CLIENTE');

-- (Si la tabla permisos existe y se desea mantener, puede ajustarse manualmente)

-- Usuarios provistos en el JSON (nota: la entidad UsuarioEntity actual contiene: nombre, email, password, rol_id)
-- Mapear tipoUsuario del JSON a roles insertados: SuperAdmin -> SUPERADMIN, Administrador -> ADMINISTRADOR, Vendedor -> VENDEDOR, Cliente -> CLIENTE
INSERT INTO usuarios (nombre, email, password, rol_id) VALUES
('Ana María Pérez Soto', 'ana.maria@gmail.cl', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'SUPERADMIN')),
('Luis Felipe González Fuentes', 'luis.felipe@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR')),
('Marcela Andrea Rojas Díaz', 'marcela.andrea@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'VENDEDOR')),
('José Ignacio Ramírez Torres', 'jose.ignacio@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'VENDEDOR')),
('Claudia Isabel Fernández Mella', 'claudia.isabel@duoc.cl', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE')),
('Andrés Eduardo Morales Castro', 'andres.eduardo@duoc.cl', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE')),
('Paola Alejandra Hernández Silva', 'paola.alex@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE')),
('Felipe Andrés Vega Carrasco', 'felipe.andres@profesor.duoc.cl', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE')),
('Carolina Beatriz Navarro Campos', 'carolina.beatriz@duoc.cl', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE')),
('Rodrigo Alonso Salazar Reyes', 'rodrigo.alonso@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', (SELECT id FROM roles WHERE nombre = 'CLIENTE'));

-- Nota: la información de 'run', 'fechaNacimiento' y 'addresses' no se insertan porque la entidad actual
-- no contiene esas columnas ni tablas relacionadas. Si desea persistir direcciones o datos adicionales,
-- puedo añadir las entidades y migraciones SQL correspondientes (tablas 'direcciones', 'usuario_addresses', etc.).

-- === Extensiones de esquema para soportar 'run', 'fechaNacimiento' y direcciones ===
-- Agregar columnas opcionales en usuarios
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS run VARCHAR(20) DEFAULT NULL;
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS fecha_nacimiento DATE DEFAULT NULL;

-- Tabla de direcciones relacionada a usuarios
CREATE TABLE IF NOT EXISTS direcciones (
	id VARCHAR(200) PRIMARY KEY,
	usuario_id BIGINT NOT NULL,
	direccion VARCHAR(500),
	region VARCHAR(200),
	comuna VARCHAR(200),
	CONSTRAINT fk_direcciones_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Insertar direcciones según JSON usando el email para resolver usuario_id
INSERT INTO direcciones (id, usuario_id, direccion, region, comuna) VALUES
('seed-ana.maria@gmail.cl', (SELECT id FROM usuarios WHERE email = 'ana.maria@gmail.cl'), 'Av. Libertador 123', 'Metropolitana', 'Santiago'),
('seed-ana.maria@gmail.cl-2', (SELECT id FROM usuarios WHERE email = 'ana.maria@gmail.cl'), 'Av. Providencia 456', 'Metropolitana', 'Providencia'),
('seed-luis.felipe@gmail.com', (SELECT id FROM usuarios WHERE email = 'luis.felipe@gmail.com'), 'Calle 5 Norte 456', 'Valparaíso', 'Viña del Mar'),
('seed-luis.felipe@gmail.com-2', (SELECT id FROM usuarios WHERE email = 'luis.felipe@gmail.com'), 'Av. Libertad 890', 'Valparaíso', 'Valparaíso'),
('seed-marcela.andrea@gmail.com', (SELECT id FROM usuarios WHERE email = 'marcela.andrea@gmail.com'), 'Pasaje Los Álamos 789', 'Biobío', 'Concepción'),
('seed-marcela.andrea@gmail.com-2', (SELECT id FROM usuarios WHERE email = 'marcela.andrea@gmail.com'), 'Av. O''Higgins 102', 'Biobío', 'Talcahuano'),
('seed-jose.ignacio@gmail.com', (SELECT id FROM usuarios WHERE email = 'jose.ignacio@gmail.com'), 'Camino a Labranza 234', 'Araucanía', 'Temuco'),
('seed-jose.ignacio@gmail.com-2', (SELECT id FROM usuarios WHERE email = 'jose.ignacio@gmail.com'), 'Av. Alemania 567', 'Araucanía', 'Temuco'),
('seed-claudia.isabel@duoc.cl', (SELECT id FROM usuarios WHERE email = 'claudia.isabel@duoc.cl'), 'Av. San Miguel 876', 'Maule', 'Talca'),
('seed-claudia.isabel@duoc.cl-2', (SELECT id FROM usuarios WHERE email = 'claudia.isabel@duoc.cl'), 'Calle 1 Sur 210', 'Maule', 'Talca'),
('seed-andres.eduardo@duoc.cl', (SELECT id FROM usuarios WHERE email = 'andres.eduardo@duoc.cl'), 'Los Perales 111', 'Coquimbo', 'La Serena'),
('seed-andres.eduardo@duoc.cl-2', (SELECT id FROM usuarios WHERE email = 'andres.eduardo@duoc.cl'), 'Av. Francisco de Aguirre 345', 'Coquimbo', 'La Serena'),
('seed-paola.alex@gmail.com', (SELECT id FROM usuarios WHERE email = 'paola.alex@gmail.com'), 'Calle Estado 222', 'O''Higgins', 'Rancagua'),
('seed-paola.alex@gmail.com-2', (SELECT id FROM usuarios WHERE email = 'paola.alex@gmail.com'), 'Av. Libertador 1010', 'O''Higgins', 'Rancagua'),
('seed-felipe.andres@profesor.duoc.cl', (SELECT id FROM usuarios WHERE email = 'felipe.andres@profesor.duoc.cl'), 'Av. Brasil 333', 'Antofagasta', 'Antofagasta'),
('seed-felipe.andres@profesor.duoc.cl-2', (SELECT id FROM usuarios WHERE email = 'felipe.andres@profesor.duoc.cl'), 'Calle Prat 215', 'Antofagasta', 'Antofagasta'),
('seed-carolina.beatriz@duoc.cl', (SELECT id FROM usuarios WHERE email = 'carolina.beatriz@duoc.cl'), 'Diego Portales 444', 'Los Lagos', 'Puerto Montt'),
('seed-rodrigo.alonso@gmail.com', (SELECT id FROM usuarios WHERE email = 'rodrigo.alonso@gmail.com'), 'Colipí 555', 'Atacama', 'Copiapó');

-- Actualizar columnas run y fecha_nacimiento en usuarios si la información está disponible (ejemplos del JSON)
UPDATE usuarios SET run = '11111111-1', fecha_nacimiento = '1990-05-12' WHERE email = 'ana.maria@gmail.cl';
UPDATE usuarios SET run = '12345678-9', fecha_nacimiento = '1985-11-20' WHERE email = 'luis.felipe@gmail.com';
UPDATE usuarios SET run = '14567832-1', fecha_nacimiento = '1978-03-08' WHERE email = 'marcela.andrea@gmail.com';
UPDATE usuarios SET run = '15678932-1', fecha_nacimiento = '1995-07-14' WHERE email = 'jose.ignacio@gmail.com';
UPDATE usuarios SET run = '16789032-2', fecha_nacimiento = '1992-10-26' WHERE email = 'claudia.isabel@duoc.cl';
UPDATE usuarios SET run = '17890132-2', fecha_nacimiento = '1980-01-22' WHERE email = 'andres.eduardo@duoc.cl';
UPDATE usuarios SET run = '18901232-2', fecha_nacimiento = '1998-12-05' WHERE email = 'paola.alex@gmail.com';
UPDATE usuarios SET run = '19011022-2', fecha_nacimiento = '1975-04-10' WHERE email = 'felipe.andres@profesor.duoc.cl';
UPDATE usuarios SET run = '20456782-2', fecha_nacimiento = '1988-08-19' WHERE email = 'carolina.beatriz@duoc.cl';
UPDATE usuarios SET run = '21567832-2', fecha_nacimiento = '1953-06-25' WHERE email = 'rodrigo.alonso@gmail.com';
