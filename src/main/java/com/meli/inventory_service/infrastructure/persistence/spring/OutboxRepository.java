package com.meli.inventory_service.infrastructure.persistence.spring;

import com.meli.inventory_service.domain.model.OutboxMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findTop100ByPublishedFalseOrderByCreatedAtAsc();
}
