INSERT INTO estados_producto (nombre, label, descripcion) VALUES
('activo', 'Activo', 'Producto disponible para la venta.'),
('inactivo', 'Inactivo', 'Producto no disponible para la venta.'),
('preparando', 'Preparando', 'Producto en preparación.');

INSERT INTO categorias (nombre_categoria, nombre_mostrar, icon, descripcion) VALUES
('Alimentos', 'Alimentos', '🍖', 'Alimentos secos, húmedos y snacks para mascotas.'),
('Juguetes', 'Juguetes', '🧸', 'Juguetes interactivos, de cuerda y peluches.'),
('Accesorios y cuidado', 'Accesorios y cuidado', '🧼', 'Correas, camas y productos de higiene para mascotas.');

INSERT INTO products (sku, nombre, descripcion, caracteristicas, imagen, precio, stock, categoria_id, activo, estado_id) VALUES
(
  'AL-101',
  'Alimento Perro Adulto',
  'Alimento seco balanceado para perros adultos, ideal para mantener energía y digestión saludable.',
  'Formato seco para perros adultos. Ayuda a mantener energía diaria, digestión saludable y una alimentación equilibrada.',
  '/img/comida-perro.png',
  29990.00,
  20,
  1,
  1,
  1
),
(
  'AL-102',
  'Alimento Gato Adulto',
  'Croquetas para gatos adultos con nutrientes esenciales para su pelaje y vitalidad.',
  'Alimento seco para gatos adultos. Aporta nutrientes esenciales para el cuidado del pelaje y la energía diaria.',
  '/img/comida-gato.png',
  24990.00,
  18,
  1,
  1,
  1
),
(
  'AL-103',
  'Snacks Naturales',
  'Premios saludables para perros y gatos, ideales para entrenamiento.',
  'Snacks en porciones pequeñas, ideales como premio durante entrenamiento o como recompensa ocasional.',
  '/img/snacks-naturales.png',
  8990.00,
  35,
  1,
  1,
  1
),
(
  'JU-201',
  'Juguete de Cuerda',
  'Cuerda resistente para entretener y ejercitar a tu perro.',
  'Juguete resistente para morder y tirar. Recomendado para actividad física y juego supervisado.',
  '/img/juguete-cuerda.png',
  6990.00,
  25,
  2,
  1,
  1
),
(
  'JU-202',
  'Pelota Mordedora',
  'Pelota texturizada para morder y estimular la actividad física.',
  'Pelota con textura para morder. Favorece el juego activo y la entretención diaria.',
  '/img/pelota.png',
  5990.00,
  30,
  2,
  1,
  1
),
(
  'JU-203',
  'Ratón de Peluche',
  'Juguete suave y liviano para estimular el instinto de juego de tu gato.',
  'Peluche pequeño y liviano para gatos. Fácil de mover, perseguir y empujar durante el juego.',
  '/img/juguete-raton.png',
  4990.00,
  40,
  2,
  1,
  1
),
(
  'AC-301',
  'Collar con Correa',
  'Set ajustable de collar y correa para paseos seguros y cómodos.',
  'Collar ajustable con correa a juego. Incluye broche seguro y material resistente para paseos.',
  '/img/collar-correa.png',
  12990.00,
  22,
  3,
  1,
  1
),
(
  'AC-302',
  'Cama para Mascota',
  'Cama acolchada para el descanso de perros o gatos pequeños.',
  'Cama acolchada con borde suave. Recomendada para mascotas pequeñas que necesitan un espacio de descanso.',
  '/img/cama-mascota.png',
  34990.00,
  12,
  3,
  1,
  1
),
(
  'AC-303',
  'Shampoo Mascotas',
  'Shampoo suave para mantener el pelaje limpio y con buen aroma.',
  'Producto de higiene para mascotas. Ayuda a limpiar el pelaje y mantener una sensación fresca.',
  '/img/shampoo.png',
  7990.00,
  28,
  3,
  1,
  1
);
