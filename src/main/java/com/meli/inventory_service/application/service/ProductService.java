package com.meli.inventory_service.application.service;

import com.meli.inventory_service.domain.model.Product;
import com.meli.inventory_service.infrastructure.persistence.spring.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(String id) {
        return repo.findById(id);
    }

    @Transactional
    public Product create(Product p) {
        if (p.getId() == null || p.getId().isBlank())
            p.setId(UUID.randomUUID().toString());
        p.setCreatedAt(Instant.now());
        p.setUpdatedAt(Instant.now());
        return repo.save(p);
    }

    @Transactional
    public Product update(String id, Product patch) {
        return repo.findById(id).map(existing -> {
            if (patch.getName() != null)
                existing.setName(patch.getName());
            if (patch.getSku() != null)
                existing.setSku(patch.getSku());
            if (patch.getDescription() != null)
                existing.setDescription(patch.getDescription());
            existing.setUpdatedAt(Instant.now());
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public void delete(String id) {
        repo.deleteById(id);
    }
}
