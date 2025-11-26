-- Inserción de datos base
-- Inserción de datos basados en el JSON de productos proporcionado
-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Tortas Cuadradas', NULL),
('Tortas Circulares', NULL),
('Postres Individuales', NULL),
('Productos sin Azúcar', NULL),
('Pastelería Tradicional', NULL),
('Productos sin Gluten', NULL),
('Productos Veganos', NULL),
('Tortas Especiales', NULL);
SELECT * FROM categorias;

-- Productos (nota: la tabla 'productos' actual contiene columnas: nombre, descripcion, precio, stock, disponible, categoria_id)
-- Usamos subconsultas para obtener la categoria_id por nombre (funciona en MySQL)
INSERT INTO productos (nombre, descripcion, precio, stock, disponible, categoria_id) VALUES
( 'Torta Cuadrada de Chocolate', 'Deliciosa torta de chocolate con varias capas de esponjoso bizcocho, rellenas de ganache de chocolate belga y un toque de avellanas tostadas. Decorada con virutas de chocolate y una cobertura brillante, es ideal para los amantes del cacao intenso. Perfecta para celebraciones especiales o para consentirte en cualquier ocasión.', 45000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Cuadradas') ),
( 'Torta Cuadrada de Frutas', 'Una mezcla exquisita de frutas frescas de temporada y crema chantilly natural sobre un suave bizcocho de vainilla. Cada bocado es una explosión de frescura y dulzura, decorada con frutas seleccionadas y glaseado ligero. Ideal para quienes buscan un postre colorido, refrescante y elegante.', 50000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Cuadradas') ),
( 'Torta Circular de Vainilla', 'Bizcocho de vainilla clásico, suave y aromático, relleno con generosa crema pastelera y cubierto con un glaseado dulce y delicado. Decorada con detalles de chocolate blanco y perlas de azúcar, es una opción tradicional que nunca falla en cumpleaños y reuniones familiares.', 40000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Circulares') ),
( 'Torta Circular de Manjar', 'Torta tradicional chilena con capas de bizcocho esponjoso, rellenas de abundante manjar artesanal y nueces trozadas. Su cobertura de merengue italiano y decoración con nueces enteras la convierten en un clásico irresistible para los fanáticos del sabor dulce y la textura crujiente.', 42000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Circulares') ),
( 'Mousse de Chocolate', 'Postre cremoso y suave, elaborado con chocolate de alta calidad y una textura aireada que se deshace en la boca. Perfecto para los amantes del chocolate, ideal como broche de oro para cualquier comida o celebración. Se sirve frío y decorado con virutas de chocolate y frutos rojos.', 5000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Postres Individuales') ),
( 'Tiramisú Clásico', 'El clásico postre italiano con capas de bizcocho empapado en café, crema de mascarpone y cacao puro. Su sabor equilibrado y textura suave lo convierten en el favorito de quienes buscan un postre sofisticado y reconfortante. Presentado en porciones individuales listas para disfrutar.', 5500, 10, true, (SELECT id FROM categorias WHERE nombre = 'Postres Individuales') ),
( 'Torta Sin Azúcar de Naranja', 'Torta ligera y deliciosa, endulzada naturalmente con jugo de naranja y edulcorantes saludables. Su bizcocho esponjoso y su aroma cítrico la hacen perfecta para quienes cuidan su consumo de azúcar sin renunciar al placer de un buen postre.', 48000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos sin Azúcar') ),
( 'Cheesecake Sin Azúcar', 'Cheesecake suave y cremoso, elaborado con queso crema light y endulzado sin azúcar refinada. Su base de galleta integral y su cobertura de frutas frescas lo hace irresistible y apto para quienes buscan opciones más saludables.', 47000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos sin Azúcar') ),
( 'Empanada de Manzana', 'Empanada tradicional rellena de manzanas frescas, canela y pasas, envuelta en una masa dorada y crujiente. Un clásico de la pastelería chilena, ideal para acompañar el té o el café de la tarde.', 3000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Pastelería Tradicional') ),
( 'Tarta de Santiago', 'Tarta española hecha con almendras molidas, azúcar y huevos, decorada con la tradicional cruz de Santiago en azúcar glas. Su sabor intenso y textura húmeda la convierten en una delicia para los amantes de la repostería europea.', 6000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Pastelería Tradicional') ),
( 'Brownie Sin Gluten', 'Brownie denso y húmedo, elaborado sin gluten pero con todo el sabor del chocolate. Ideal para personas celíacas o quienes buscan alternativas más saludables, sin sacrificar el placer de un buen postre.', 3500, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos sin Gluten') ),
( 'Pan Sin Gluten', 'Pan suave y esponjoso, libre de gluten, perfecto para acompañar cualquier comida o preparar deliciosos sándwiches. Su sabor neutro y textura ligera lo hacen apto para toda la familia.', 3500, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos sin Gluten') ),
( 'Torta Vegana de Chocolate', 'Torta húmeda y esponjosa, elaborada sin productos de origen animal. Rellena de crema de chocolate vegana y decorada con frutas frescas o frutos secos. Una opción deliciosa y ética para quienes siguen una dieta vegana.', 38000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos Veganos') ),
( 'Galletas Veganas de Avena', 'Galletas crujientes y sabrosas, hechas con avena integral, plátano y chips de chocolate vegano. Son una opción saludable y energética para disfrutar en cualquier momento del día.', 4500, 10, true, (SELECT id FROM categorias WHERE nombre = 'Productos Veganos') ),
( 'Torta Especial de Cumpleaños', 'Celebra a lo grande con una torta de cumpleaños totalmente personalizable: elige sabores, colores y decoraciones temáticas. Rellena de crema suave y frutas o chocolate, y decorada con fondant artístico, figuras y mensajes especiales. Sorprende a tus seres queridos con una torta única y deliciosa, hecha a tu medida.', 55000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Especiales') ),
( 'Torta Especial de Boda', 'Elegante torta de boda de varios pisos, elaborada con ingredientes premium y decorada con flores naturales o de azúcar. Rellena de crema de vainilla, frutos rojos o chocolate, según tu preferencia. Un centro de mesa espectacular y delicioso para el día más importante de tu vida.', 60000, 10, true, (SELECT id FROM categorias WHERE nombre = 'Tortas Especiales') );
SELECT * FROM productos;

-- === Extensiones de esquema para soportar el JSON completo ===
-- Agregar columnas opcionales a productos si no existen (MySQL 8+)
ALTER TABLE productos ADD COLUMN IF NOT EXISTS codigo VARCHAR(50) DEFAULT NULL;
ALTER TABLE productos ADD COLUMN IF NOT EXISTS imagen VARCHAR(255) DEFAULT NULL;
ALTER TABLE productos ADD COLUMN IF NOT EXISTS stock_critico INT DEFAULT 0;

-- Tabla para ratings / valoraciones (relacionada a productos)
CREATE TABLE IF NOT EXISTS ratings (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	producto_id BIGINT NOT NULL,
	userEmail VARCHAR(255) NOT NULL,
	userName VARCHAR(255),
	stars INT NOT NULL,
	comment TEXT,
	fecha DATETIME,
	CONSTRAINT fk_ratings_producto FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);

-- Actualizar productos con códigos e imágenes según JSON
UPDATE productos SET codigo = 'TC001', imagen = '/images/products/tortas/tradicional/torta-cuadrada-chocolate.png', stock_critico = 3 WHERE nombre LIKE '%Torta Cuadrada de Chocolate%';
UPDATE productos SET codigo = 'TC002', imagen = '/images/products/tortas/tradicional/torta-cuadrada-frutas.jpg', stock_critico = 3 WHERE nombre LIKE '%Torta Cuadrada de Frutas%';
UPDATE productos SET codigo = 'TT001', imagen = '/images/products/tortas/tradicional/torta-circular-vainilla.jpg', stock_critico = 3 WHERE nombre LIKE '%Torta Circular de Vainilla%';
UPDATE productos SET codigo = 'TT002', imagen = '/images/products/tortas/tradicional/torta-circular-manjar.jpg', stock_critico = 3 WHERE nombre LIKE '%Torta Circular de Manjar%';
UPDATE productos SET codigo = 'PI001', imagen = '/images/products/postres-individuales/mousse-chocolate.png', stock_critico = 3 WHERE nombre LIKE '%Mousse de Chocolate%';
UPDATE productos SET codigo = 'PI002', imagen = '/images/products/postres-individuales/tiramisu.jpg', stock_critico = 3 WHERE nombre LIKE '%Tiramisú Clásico%';
UPDATE productos SET codigo = 'PSA001', imagen = '/images/products/tortas/sin-azucar/torta-sin-azucar-naranja.png', stock_critico = 3 WHERE nombre LIKE '%Torta Sin Azúcar de Naranja%';
UPDATE productos SET codigo = 'PSA002', imagen = '/images/products/cheesecake/sin-azucar/cheesecake-sin-azucar.avif', stock_critico = 3 WHERE nombre LIKE '%Cheesecake Sin Azúcar%';
UPDATE productos SET codigo = 'PT001', imagen = '/images/products/pasteleria-tradicional/empanada-manzana.jpg', stock_critico = 3 WHERE nombre LIKE '%Empanada de Manzana%';
UPDATE productos SET codigo = 'PT002', imagen = '/images/products/pasteleria-tradicional/tarta-santiago.png', stock_critico = 3 WHERE nombre LIKE '%Tarta de Santiago%';
UPDATE productos SET codigo = 'PG001', imagen = '/images/products/sin-gluten/brownie-sin-gluten.jpg', stock_critico = 3 WHERE nombre LIKE '%Brownie Sin Gluten%';
UPDATE productos SET codigo = 'PG002', imagen = '/images/products/sin-gluten/pan-sin-gluten.jpg', stock_critico = 3 WHERE nombre LIKE '%Pan Sin Gluten%';
UPDATE productos SET codigo = 'PV001', imagen = '/images/products/tortas/vegana/vegana-chocolate.jpg', stock_critico = 3 WHERE nombre LIKE '%Torta Vegana de Chocolate%';
UPDATE productos SET codigo = 'PV002', imagen = '/images/products/galletas/vegana/galletas-vegana.jpg', stock_critico = 3 WHERE nombre LIKE '%Galletas Veganas de Avena%';
UPDATE productos SET codigo = 'TE001', imagen = '/images/products/tortas/especial/torta-especial-cumpleaños.png', stock_critico = 3 WHERE nombre LIKE '%Torta Especial de Cumpleaños%';
UPDATE productos SET codigo = 'TE002', imagen = '/images/products/tortas/especial/torta-especial-boda.jpeg', stock_critico = 3 WHERE nombre LIKE '%Torta Especial de Boda%';

-- Insertar ratings según JSON
INSERT INTO ratings (producto_id, userEmail, userName, stars, comment, fecha) VALUES
((SELECT id FROM productos WHERE codigo = 'TC001'), 'claudia.isabel@duoc.cl', 'Claudia Isabel', 5, 'Excelente sabor y presentación.', '2025-10-01 12:00:00'),
((SELECT id FROM productos WHERE codigo = 'TC001'), 'paola.alex@gmail.com', 'Paola Alejandra', 4, 'Muy buena, un poco dulce para mi gusto.', '2025-10-12 09:30:00'),
((SELECT id FROM productos WHERE codigo = 'TT001'), 'andres.eduardo@duoc.cl', 'Andrés Eduardo', 5, 'Perfecta para cumpleaños.', '2025-09-21 15:45:00'),
((SELECT id FROM productos WHERE codigo = 'PI001'), 'carolina.beatriz@duoc.cl', 'Carolina Beatriz', 4, 'Muy rico, textura excelente.', '2025-11-01 11:20:00'),
((SELECT id FROM productos WHERE codigo = 'PG001'), 'felipe.andres@profesor.duoc.cl', 'Felipe Andrés', 5, 'Increíble textura, no parece sin gluten.', '2025-08-15 08:00:00'),
((SELECT id FROM productos WHERE codigo = 'PG001'), 'rodrigo.alonso@gmail.com', 'Rodrigo Alonso', 4, 'Buen sabor, un poco pequeño.', '2025-10-05 14:10:00');