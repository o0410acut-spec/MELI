-- Limpiar tablas existentes
DELETE FROM store_inventory;
DELETE FROM outbox_message;

-- Datos de inventario actualizados
INSERT INTO store_inventory (store_id, product_id, total_quantity, reserved_quantity, updated_at, version) 
VALUES 
('store-001', 'sku-100', 100, 0, CURRENT_TIMESTAMP(), 0),
('store-001', 'sku-101', 50, 0, CURRENT_TIMESTAMP(), 0),
('store-002', 'sku-100', 75, 0, CURRENT_TIMESTAMP(), 0),
('store-002', 'sku-102', 200, 0, CURRENT_TIMESTAMP(), 0);

-- Datos de outbox actualizados
INSERT INTO outbox_message (aggregate_type, aggregate_id, topic, payload, published, created_at)
VALUES 
('StoreInventory', 'store-001-sku-100', 'inventory.created', '{"storeId":"store-001","productId":"sku-100"}', false, CURRENT_TIMESTAMP());
