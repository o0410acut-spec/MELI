package com.meli.inventory_service.infrastructure.rest.dto;

import com.meli.inventory_service.domain.model.StoreInventory;

public class StoreInventoryResponse {
    private String storeId;
    private String productId;
    private int totalQuantity;
    private int reservedQuantity;
    private int available;

    public static StoreInventoryResponse fromDomain(StoreInventory inventory) {
        StoreInventoryResponse response = new StoreInventoryResponse();
        response.setStoreId(inventory.getStoreId());
        response.setProductId(inventory.getProductId());
        response.setTotalQuantity(inventory.getTotalQuantity());
        response.setReservedQuantity(inventory.getReservedQuantity());
        response.setAvailable(inventory.getAvailable());
        return response;
    }

    // Getters and Setters
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
