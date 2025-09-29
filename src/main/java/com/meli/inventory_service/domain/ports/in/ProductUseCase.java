package com.meli.inventory_service.domain.ports.in;

import com.meli.inventory_service.domain.model.Product;
import java.util.List;

public interface ProductUseCase {
    List<Product> getAllProducts();

    Product getProductById(String id);

    void updateStock(String id, int newStock);

    Product createProduct(String id, String name, double price, int stock);
}
