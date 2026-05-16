CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(38,2) NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    activo BIT NOT NULL
);