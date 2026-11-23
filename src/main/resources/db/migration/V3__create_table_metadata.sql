CREATE TABLE columns_metadata (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(64) NOT NULL,
    column_name VARCHAR(64) NOT NULL,
    api_key VARCHAR(64) NOT NULL,
    label VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    show_in_create BOOLEAN NOT NULL DEFAULT TRUE,
    show_in_edit BOOLEAN NOT NULL DEFAULT TRUE,
    required BOOLEAN NOT NULL DEFAULT FALSE,
    is_read_only BOOLEAN NOT NULL DEFAULT FALSE,
    is_searchable BOOLEAN NOT NULL DEFAULT FALSE,
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    highlight BOOLEAN NOT NULL DEFAULT FALSE,
    is_state BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uk_columns_metadata_table_api_key UNIQUE (table_name, api_key),
    CONSTRAINT uk_columns_metadata_table_column UNIQUE (table_name, column_name)
);

INSERT INTO columns_metadata (
    table_name,
    column_name,
    api_key,
    label,
    type,
    sort_order,
    show_in_create,
    show_in_edit,
    required,
    is_read_only,
    is_searchable,
    is_hidden,
    highlight,
    is_state
) VALUES
('productos', 'id', 'id', 'ID', 'string', 1, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, FALSE),
('productos', 'nombre', 'nombre', 'Producto', 'string', 2, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE),
('productos', 'sku', 'sku', 'SKU', 'string', 3, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE),
('productos', 'descripcion', 'descripcion', 'Descripción', 'text', 4, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE),
('productos', 'categoria_id', 'categoriaId', 'Categoría', 'string', 5, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE),
('productos', 'precio', 'precio', 'Precio', 'money', 6, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE),
('productos', 'stock', 'stock', 'Stock', 'int', 7, TRUE, TRUE, FALSE, FALSE, TRUE, FALSE, FALSE, FALSE),
('productos', 'estado_id', 'estadoId', 'Estado', 'string', 8, TRUE, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, TRUE),
('productos', 'imagen', 'imagen', 'Imagen', 'string', 9, TRUE, TRUE, TRUE, FALSE, FALSE, TRUE, FALSE, FALSE),
('productos', 'caracteristicas', 'caracteristicas', 'Características', 'text', 10, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE)
;
