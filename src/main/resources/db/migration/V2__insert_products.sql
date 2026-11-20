INSERT INTO estados_producto (nombre, label, descripcion) VALUES
('activo', 'Activo', 'Producto disponible para la venta.'),
('inactivo', 'Inactivo', 'Producto no disponible para la venta.'),
('preparando', 'Preparando', 'Producto en preparación.');

INSERT INTO categorias (nombre_categoria, nombre_mostrar, icon, descripcion) VALUES
('Alimentos para perros', 'Alimentos para perros', '🐕', 'Alimento y nutrición para perros.'),
('Alimentos para gatos', 'Alimentos para gatos', '🐈', 'Alimento y nutrición para gatos.'),
('Higiene', 'Higiene', '✂️', 'Productos de higiene y aseo.'),
('Cuidado e higiene', 'Cuidado e higiene', '🩺', 'Cuidado e higiene específica.'),
('Accesorios', 'Accesorios', '🎒', 'Accesorios para mascotas.'),
('Descanso', 'Descanso', '🛏️', 'Camas y descanso.'),
('Snacks', 'Snacks', '🦴', 'Premios y snacks.'),
('Juguetes', 'Juguetes', '🧸', 'Juguetes para mascotas.');

INSERT INTO products (sku, nombre, descripcion, precio, stock, categoria_id, activo, estado_id)
VALUES
('PD-101', 'Alimento Dog Chow Adulto 15kg', 'Alimento seco para perros adultos de raza mediana y grande', 32990.00, 20, 1, 1, 1),

('PG-201', 'Alimento Cat Chow Adulto 8kg', 'Alimento seco para gatos adultos con vitaminas y minerales', 26990.00, 18, 2, 1, 1),

('HG-601', 'Arena Sanitaria para Gatos 10kg', 'Arena sanitaria aglomerante con control de olores', 9990.00, 35, 3, 1, 1),

('HG-602', 'Shampoo para Perros 500ml', 'Shampoo suave para perros con aroma fresco', 5990.00, 25, 4, 1, 1),

('AC-701', 'Correa Ajustable para Perro', 'Correa resistente ajustable para paseo diario', 7990.00, 30, 5, 1, 3),

('DE-801', 'Cama para Mascota Mediana', 'Cama acolchada para perros y gatos medianos', 24990.00, 12, 6, 1, 3),

('SN-901', 'Snack Dental para Perros 500g', 'Premios dentales para ayudar al cuidado bucal del perro', 8490.00, 22, 7, 1, 1),

('AC-702', 'Comedero Doble Antideslizante', 'Comedero doble para agua y alimento con base antideslizante', 6990.00, 28, 5, 1, 1),

('JU-901', 'Juguete Mordedor de Goma', 'Juguete resistente para perros pequeños y medianos', 4990.00, 40, 8, 1, 3),

('PG-202', 'Alimento Húmedo para Gatos 85g', 'Sobre de alimento húmedo sabor salmón para gatos adultos', 1290.00, 60, 2, 1, 1);
