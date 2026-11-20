CREATE TABLE estados_producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    nombre_mostrar VARCHAR(50) NOT NULL,
    icon VARCHAR(32) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(64) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(38,2) NOT NULL,
    stock INT NOT NULL,
    categoria_id BIGINT NOT NULL,
    activo BIT NOT NULL,
    estado_id BIGINT NOT NULL,
    CONSTRAINT uk_products_sku UNIQUE (sku),
    CONSTRAINT fk_products_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_products_estado FOREIGN KEY (estado_id) REFERENCES estados_producto(id)
);
