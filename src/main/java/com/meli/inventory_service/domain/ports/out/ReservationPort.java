package com.meli.inventory_service.domain.ports.out;

import com.meli.inventory_service.domain.model.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationPort {
    Reservation save(Reservation r);

    Optional<Reservation> findById(String reservationId);

    List<Reservation> findExpired(int limit);
}
