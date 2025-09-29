package com.meli.inventory_service.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class InventoryMetrics {
    private final Counter reservationsCounter;

    public InventoryMetrics(MeterRegistry registry) {
        this.reservationsCounter = Counter.builder("inventory.reservations.total")
                .description("Total number of inventory reservations")
                .register(registry);
    }

    public void incrementReservations() {
        reservationsCounter.increment();
    }
}
