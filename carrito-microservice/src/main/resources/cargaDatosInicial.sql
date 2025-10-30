-- Inserción de datos base
-- Crear carritos de prueba (uno por usuario)
INSERT INTO carritos (usuario_id, fecha_creacion) VALUES
(3, sysdate()),  -- Antonia (cliente)
(2, sysdate());  -- Carlos (vendedor, solo para prueba)
SELECT * FROM carritos;
-- Insertar ítems de ejemplo
INSERT INTO items_carrito (producto_id, nombre_producto, precio_unitario, cantidad, carrito_id) VALUES
(1, 'Torta Tres Leches', 12990, 1, 1),
(4, 'Cupcake Vainilla', 2490, 6, 1),
(2, 'Torta de Chocolate', 13990, 1, 2);
SELECT * FROM items_carrito;