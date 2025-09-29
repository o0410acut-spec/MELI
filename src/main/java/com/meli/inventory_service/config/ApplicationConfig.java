package com.meli.inventory_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.inventory_service.application.service.InventoryUseCase;
import com.meli.inventory_service.domain.ports.out.InventoryPort;
import com.meli.inventory_service.domain.ports.out.OutboxPort;
import com.meli.inventory_service.domain.ports.out.ReservationPort;
import com.meli.inventory_service.infrastructure.metrics.InventoryMetrics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public InventoryUseCase inventoryUseCase(InventoryPort inventoryPort,
            ReservationPort reservationPort,
            OutboxPort outboxPort,
            ObjectMapper objectMapper,
            InventoryMetrics inventoryMetrics,
            @Value("${inventory.reservation.ttl.seconds:600}") long ttl) {
        return new InventoryUseCase(
                inventoryPort,
                reservationPort,
                outboxPort,
                ttl,
                objectMapper,
                inventoryMetrics);
    }
}
