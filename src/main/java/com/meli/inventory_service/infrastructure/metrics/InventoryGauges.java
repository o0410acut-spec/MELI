package com.meli.inventory_service.infrastructure.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InventoryGauges {
    private final AtomicInteger available = new AtomicInteger(0);

    public InventoryGauges(MeterRegistry registry) {
        // registra un gauge que lee el AtomicInteger
        Gauge.builder("inventory_available_total", available, AtomicInteger::get)
                .description("Stock total disponible (sumado)")
                .register(registry);
    }

    // Llama esto cuando quieras actualizar el valor desde tu código
    public void setAvailable(int v) {
        available.set(v);
    }

    // lecturas opcionales
    public int getAvailable() {
        return available.get();
    }
}
