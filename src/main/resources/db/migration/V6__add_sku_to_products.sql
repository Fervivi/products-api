ALTER TABLE products ADD COLUMN sku VARCHAR(64) NULL AFTER id;

UPDATE products SET sku = CASE nombre
    WHEN 'Alimento Dog Chow Adulto 15kg' THEN 'PD-101'
    WHEN 'Alimento Cat Chow Adulto 8kg' THEN 'PG-201'
    WHEN 'Arena Sanitaria para Gatos 10kg' THEN 'HG-601'
    WHEN 'Shampoo para Perros 500ml' THEN 'HG-602'
    WHEN 'Correa Ajustable para Perro' THEN 'AC-701'
    WHEN 'Cama para Mascota Mediana' THEN 'DE-801'
    WHEN 'Snack Dental para Perros 500g' THEN 'SN-901'
    WHEN 'Comedero Doble Antideslizante' THEN 'AC-702'
    WHEN 'Juguete Mordedor de Goma' THEN 'JU-901'
    WHEN 'Alimento Húmedo para Gatos 85g' THEN 'PG-202'
    ELSE CONCAT('GEN-', LPAD(id, 3, '0'))
END;

ALTER TABLE products MODIFY COLUMN sku VARCHAR(64) NOT NULL;

ALTER TABLE products ADD CONSTRAINT uk_products_sku UNIQUE (sku);
