CREATE TABLE estados_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);

INSERT INTO estados_producto (nombre, label, descripcion) VALUES
('activo', 'Activo', 'Producto disponible para la venta.'),
('inactivo', 'Inactivo', 'Producto no disponible para la venta.'),
('preparando', 'Preparando', 'Producto en preparación.');
