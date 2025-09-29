package com.meli.inventory_service.infrastructure.rest;

import com.meli.inventory_service.application.service.ProductService;
import com.meli.inventory_service.domain.model.Product;
import com.meli.inventory_service.infrastructure.rest.dto.ProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService svc;

    public ProductController(ProductService svc) {
        this.svc = svc;
    }

    @GetMapping
    public ResponseEntity<List<Product>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable String id) {
        return svc.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setSku(req.getSku()); // Añadir SKU
        product.setDescription(req.getDescription());

        try {
            Product created = svc.create(product);
            return ResponseEntity.created(URI.create("/api/v1/products/" + created.getId()))
                    .body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody ProductRequest req) {
        Product patch = new Product();
        patch.setSku(req.getSku());
        patch.setName(req.getName());
        patch.setDescription(req.getDescription());
        return ResponseEntity.ok(svc.update(id, patch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
