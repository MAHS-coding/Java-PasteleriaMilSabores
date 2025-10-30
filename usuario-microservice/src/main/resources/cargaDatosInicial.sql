-- Inserción de datos base
INSERT INTO roles (nombre)
VALUES ('ADMINISTRADOR'), ('VENDEDOR'), ('CLIENTE');

INSERT INTO permisos (nombre)
VALUES ('GESTIONAR_USUARIOS'), ('VER_CATALOGO'), ('GESTIONAR_PRODUCTOS'), ('REALIZAR_COMPRAS'), ('GESTIONAR_PEDIDOS');

-- Asignar permisos a roles
INSERT INTO rol_permisos (rol_id, permiso_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),  -- Admin: todos los permisos
(2, 2), (2, 3), (2, 5),                 -- Vendedor: catálogo, productos y pedidos
(3, 2), (3, 4);                         -- Cliente: catálogo y compras

-- Usuarios de prueba
INSERT INTO usuarios (nombre, email, password) VALUES
('Fernanda Torres', 'fer@milsabores.cl', '1234'),
('Carlos Soto', 'carlos@milsabores.cl', '1234'),
('Antonia Pérez', 'anto@gmail.com', '1234');

-- Asignar roles
INSERT INTO usuario_roles (usuario_id, rol_id) VALUES
(1, 1),  -- Fernanda = Administradora
(2, 2),  -- Carlos = Vendedor
(3, 3);  -- Antonia = Cliente
