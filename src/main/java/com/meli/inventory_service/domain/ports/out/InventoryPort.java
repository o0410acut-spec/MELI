package com.meli.inventory_service.domain.ports.out;

import com.meli.inventory_service.domain.model.StoreInventory;
import java.util.List;
import java.util.Optional;

public interface InventoryPort {
    Optional<StoreInventory> findByStoreAndProduct(String storeId, String productId);

    StoreInventory save(StoreInventory inventory);

    List<StoreInventory> findAll();
}
