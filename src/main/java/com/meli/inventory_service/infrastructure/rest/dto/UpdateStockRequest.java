package com.meli.inventory_service.infrastructure.rest.dto;

public class UpdateStockRequest {
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
