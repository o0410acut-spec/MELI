package com.meli.inventory_service.infrastructure.rest;

import com.meli.inventory_service.application.service.ProductService;
import com.meli.inventory_service.domain.model.Product;
import com.meli.inventory_service.infrastructure.rest.dto.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
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
    public ResponseEntity<Product> create(@RequestBody ProductRequest req) {
        Product p = new Product();
        p.setId(req.getId());
        p.setSku(req.getSku());
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        Product created = svc.create(p);
        return ResponseEntity.created(URI.create("/products/" + created.getId())).body(created);
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
