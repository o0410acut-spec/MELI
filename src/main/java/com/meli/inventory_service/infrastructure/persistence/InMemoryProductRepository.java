// package com.meli.inventory_service.infrastructure.persistence;

// import com.meli.inventory_service.domain.model.Product;
// import
// com.meli.inventory_service.infrastructure.persistence.spring.ProductRepository;

// import org.springframework.stereotype.Repository;
// import java.util.*;

// @Repository
// public class InMemoryProductRepository implements ProductRepository {
// private final Map<String, Product> products = new HashMap<>();

// @Override
// public Product findById(String id) {
// return products.get(id);
// }

// @Override
// public void save(Product product) {
// products.put(product.getId(), product);
// }

// @Override
// public List<Product> findAll() {
// return new ArrayList<>(products.values());
// }
// }
