CREATE TABLE estados_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(64) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(38,2) NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    activo BIT NOT NULL,
    CONSTRAINT uk_products_sku UNIQUE (sku)
);
