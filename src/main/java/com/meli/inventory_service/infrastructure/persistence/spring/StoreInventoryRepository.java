package com.meli.inventory_service.infrastructure.persistence.spring;

import com.meli.inventory_service.domain.model.StoreInventory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    Optional<StoreInventory> findByStoreIdAndProductId(String storeId, String productId);
}
