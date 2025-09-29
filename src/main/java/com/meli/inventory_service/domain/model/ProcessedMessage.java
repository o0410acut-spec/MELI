package com.meli.inventory_service.domain.model;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "processed_message", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "message_id", "consumer_name" })
})
public class ProcessedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "consumer_name", nullable = false)
    private String consumerName;

    @Column(name = "processed_at")
    private Instant processedAt = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }
}
