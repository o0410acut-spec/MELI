package com.meli.inventory_service.infrastructure.persistence;

import com.meli.inventory_service.domain.ports.out.ReservationPort;
import com.meli.inventory_service.domain.model.Reservation;
import com.meli.inventory_service.infrastructure.persistence.spring.ReservationRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;
import java.time.Instant;

@Component
public class JpaReservationAdapter implements ReservationPort {
    private final ReservationRepository repo;

    public JpaReservationAdapter(ReservationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Reservation save(Reservation r) {
        return repo.save(r);
    }

    @Override
    public Optional<Reservation> findById(String reservationId) {
        return repo.findById(reservationId);
    }

    @Override
    public List<Reservation> findExpired(int limit) {
        return repo.findExpiredReservations(Instant.now(), limit);
    }
}
