package com.meli.inventory_service.infrastructure.persistence.spring;

import com.meli.inventory_service.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    // puedes añadir consultas personalizadas si las necesitas luego

    @Query("""
            select r from Reservation r
            where r.status = 'PENDING'
            and r.expiresAt < :now
            order by r.expiresAt asc
            limit :limit
            """)
    List<Reservation> findExpiredReservations(Instant now, int limit);
}
