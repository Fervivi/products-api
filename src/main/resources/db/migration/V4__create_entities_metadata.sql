CREATE TABLE entities_metadata (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(64) NOT NULL,
    entity_key VARCHAR(64) NOT NULL,
    single_label VARCHAR(100) NOT NULL,
    plural_label VARCHAR(100) NOT NULL,
    category_field VARCHAR(64) NOT NULL,
    category_label VARCHAR(100) NOT NULL,
    category_add_title VARCHAR(100) NOT NULL,
    category_icons JSON NOT NULL,
    delete_name_key VARCHAR(64) NOT NULL,
    delete_detail_key VARCHAR(64) NOT NULL,
    delete_detail_label VARCHAR(100) NOT NULL,
    action_key VARCHAR(64) NOT NULL,
    action_label VARCHAR(100) NOT NULL,
    CONSTRAINT uk_entities_metadata_table_name UNIQUE (table_name),
    CONSTRAINT uk_entities_metadata_entity_key UNIQUE (entity_key)
);

INSERT INTO entities_metadata (
    table_name,
    entity_key,
    single_label,
    plural_label,
    category_field,
    category_label,
    category_add_title,
    category_icons,
    delete_name_key,
    delete_detail_key,
    delete_detail_label,
    action_key,
    action_label
) VALUES (
    'productos',
    'producto',
    'Producto',
    'Productos',
    'categoria',
    'Categoría',
    'Nueva Categoría',
    JSON_ARRAY('🐾', '🐕', '🐈', '🐦', '🐟', '🐹', '🦴', '🥣', '🧸', '🧶', '🛏️', '🏠', '🦮', '🎒', '🩺', '✂️', '👕', '📦'),
    'nombre',
    'sku',
    'SKU',
    'accion',
    'Acciones'
);
