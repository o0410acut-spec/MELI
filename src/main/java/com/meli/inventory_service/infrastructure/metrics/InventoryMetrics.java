package com.meli.inventory_service.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class InventoryMetrics {

    private final Counter reservationsCounter;
    private final Counter productsCreatedCounter;

    public InventoryMetrics(MeterRegistry registry) {
        this.reservationsCounter = Counter.builder("inventory_reservations.total")
                .description("Total number of inventory reservations")
                .register(registry);

        this.productsCreatedCounter = Counter.builder("inventory_products_created.total")
                .description("Total number of products created")
                .register(registry);
    }

    // reservas
    public void incrementReservations() {
        reservationsCounter.increment();
    }

    // productos creados
    public void incrementProductsCreated() {
        productsCreatedCounter.increment();
    }
}
