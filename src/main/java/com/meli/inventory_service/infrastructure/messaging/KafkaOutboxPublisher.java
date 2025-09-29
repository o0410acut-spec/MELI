package com.meli.inventory_service.infrastructure.messaging;

import com.meli.inventory_service.domain.ports.out.OutboxPort;
import com.meli.inventory_service.domain.model.OutboxMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class KafkaOutboxPublisher {
    private final OutboxPort outboxPort;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaOutboxPublisher(OutboxPort outboxPort, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxPort = outboxPort;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void publishPending() {
        List<OutboxMessage> pending = outboxPort.fetchPending(100);
        for (OutboxMessage m : pending) {
            try {
                kafkaTemplate.send(m.getTopic(), m.getAggregateId(), m.getPayload()).get();
                outboxPort.markPublished(m.getId());
            } catch (Exception e) {
                System.err.println("Error publishing outbox id=" + m.getId() + " -> " + e.getMessage());
            }
        }
    }
}
