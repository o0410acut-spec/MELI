package com.meli.inventory_service.application.jobs;

import com.meli.inventory_service.domain.ports.in.InventoryUseCasePort;
import com.meli.inventory_service.domain.ports.out.ReservationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpirationJob {
    private static final Logger log = LoggerFactory.getLogger(ReservationExpirationJob.class);
    private final ReservationPort reservationPort;
    private final InventoryUseCasePort inventoryUseCase;

    public ReservationExpirationJob(
            ReservationPort reservationPort,
            InventoryUseCasePort inventoryUseCase) {
        this.reservationPort = reservationPort;
        this.inventoryUseCase = inventoryUseCase;
    }

    @Scheduled(fixedRate = 60000) // Ejecutar cada minuto
    public void processExpiredReservations() {
        int processed = 0;
        try {
            var expired = reservationPort.findExpired(100);
            if (!expired.isEmpty()) {
                log.info("Found {} expired reservations to process", expired.size());
            }

            for (var reservation : expired) {
                try {
                    log.info("Processing expired reservation={}, transactionId={}, storeId={}, productId={}",
                            reservation.getReservationId(),
                            reservation.getTransactionId(),
                            reservation.getStoreId(),
                            reservation.getProductId());

                    inventoryUseCase.release(reservation.getReservationId(), "expired");
                    processed++;
                } catch (Exception e) {
                    log.error("Failed to release reservation={}, transactionId={}: {}",
                            reservation.getReservationId(),
                            reservation.getTransactionId(),
                            e.getMessage());
                }
            }
            if (processed > 0) {
                log.info("Successfully processed {} expired reservations", processed);
            }
        } catch (Exception e) {
            log.error("Error in expiration job: {}", e.getMessage(), e);
        }
    }
}
