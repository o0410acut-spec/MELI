package com.meli.inventory_service.infrastructure.persistence.spring;

import com.meli.inventory_service.domain.model.ProcessedMessage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, Long> {
    Optional<ProcessedMessage> findByMessageIdAndConsumerName(String messageId, String consumerName);
}
