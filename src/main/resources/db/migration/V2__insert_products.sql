INSERT INTO estados_producto (nombre, label, descripcion) VALUES
('activo', 'Activo', 'Producto disponible para la venta.'),
('inactivo', 'Inactivo', 'Producto no disponible para la venta.'),
('preparando', 'Preparando', 'Producto en preparación.');

INSERT INTO products (sku, nombre, descripcion, precio, stock, categoria, activo, estado_id)
VALUES
('PD-101', 'Alimento Dog Chow Adulto 15kg', 'Alimento seco para perros adultos de raza mediana y grande', 32990.00, 20, 'Alimentos para perros', 1, 1),

('PG-201', 'Alimento Cat Chow Adulto 8kg', 'Alimento seco para gatos adultos con vitaminas y minerales', 26990.00, 18, 'Alimentos para gatos', 1, 1),

('HG-601', 'Arena Sanitaria para Gatos 10kg', 'Arena sanitaria aglomerante con control de olores', 9990.00, 35, 'Higiene', 1, 1),

('HG-602', 'Shampoo para Perros 500ml', 'Shampoo suave para perros con aroma fresco', 5990.00, 25, 'Cuidado e higiene', 1, 1),

('AC-701', 'Correa Ajustable para Perro', 'Correa resistente ajustable para paseo diario', 7990.00, 30, 'Accesorios', 1, 3),

('DE-801', 'Cama para Mascota Mediana', 'Cama acolchada para perros y gatos medianos', 24990.00, 12, 'Descanso', 1, 3),

('SN-901', 'Snack Dental para Perros 500g', 'Premios dentales para ayudar al cuidado bucal del perro', 8490.00, 22, 'Snacks', 1, 1),

('AC-702', 'Comedero Doble Antideslizante', 'Comedero doble para agua y alimento con base antideslizante', 6990.00, 28, 'Accesorios', 1, 1),

('JU-901', 'Juguete Mordedor de Goma', 'Juguete resistente para perros pequeños y medianos', 4990.00, 40, 'Juguetes', 1, 3),

('PG-202', 'Alimento Húmedo para Gatos 85g', 'Sobre de alimento húmedo sabor salmón para gatos adultos', 1290.00, 60, 'Alimentos para gatos', 1, 1);
