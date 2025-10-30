-- Inserción de datos base
INSERT INTO categorias (nombre) VALUES
('Tortas Clásicas'),
('Tortas Personalizadas'),
('Cupcakes'),
('Galletas Decoradas');
SELECT * FROM categorias;
-- Insertar productos
INSERT INTO productos (nombre, descripcion, precio, stock, disponible, categoria_id) VALUES
('Torta Tres Leches', 'Torta húmeda con crema pastelera y merengue', 12990, 15, true,  1),
('Torta de Chocolate', 'Bizcocho de cacao con relleno de ganache', 13990, 20, true,  1),
('Torta Personalizada', 'Permite agregar un mensaje especial', 15990, 10, true, 2),
('Cupcake Vainilla', 'Cupcakes con crema de vainilla', 2490, 50, true, 3),
('Galletas Decoradas', 'Galletas de mantequilla decoradas con glaseado', 1990, 80, true, 4);
SELECT * FROM productos;