package com.meli.inventory_service.domain.ports.in;

import com.meli.inventory_service.domain.model.StoreInventory;
import com.meli.inventory_service.infrastructure.rest.dto.ReserveRequest;
import com.meli.inventory_service.infrastructure.rest.dto.ReserveResponse;

import java.util.List;

public interface InventoryUseCasePort {
    ReserveResponse reserve(ReserveRequest req);

    void commit(String reservationId);

    void release(String reservationId, String reason);

    List<StoreInventory> getAllInventories();
}
