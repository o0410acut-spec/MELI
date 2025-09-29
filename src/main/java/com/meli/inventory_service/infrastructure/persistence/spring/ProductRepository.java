package com.meli.inventory_service.infrastructure.persistence.spring;

import com.meli.inventory_service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
