-- Insertar categorías para SmokingCute
INSERT INTO categorias (nombre, descripcion, imagen_url) VALUES
('Ceniceros Clásicos', 'Ceniceros tradicionales y elegantes para cualquier ocasión', '/img/ceniceros-clasicos.jpg'),
('Ceniceros Modernos', 'Diseños contemporáneos y minimalistas', '/img/ceniceros-modernos.jpg'),
('Ceniceros Personalizados', 'Ceniceros con diseños personalizados y únicos', '/img/ceniceros-personalizados.jpg'),
('Ceniceros de Lujo', 'Ceniceros premium con materiales exclusivos', '/img/ceniceros-lujo.jpg'),
('Ceniceros de Viaje', 'Ceniceros portátiles y prácticos para viajes', '/img/ceniceros-viaje.jpg');

-- Insertar productos de ejemplo
INSERT INTO productos (nombre, descripcion, precio, categoria_id, imagen_url, stock, personalizable, material, dimensiones, stock_minimo) VALUES
('Cenicero Clásico Cristal', 'Elegante cenicero de cristal tallado, ideal para interiores sofisticados', 29.99, 1, '/img/cenicero-cristal.jpg', 50, false, 'Cristal', '10x10x5 cm', 5),
('Cenicero Minimalista Acero', 'Diseño minimalista en acero inoxidable, fácil de limpiar', 24.99, 2, '/img/c