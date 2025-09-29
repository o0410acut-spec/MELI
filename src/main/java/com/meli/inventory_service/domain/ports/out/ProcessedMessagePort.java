package com.meli.inventory_service.domain.ports.out;

import com.meli.inventory_service.domain.model.ProcessedMessage;
import java.util.Optional;

public interface ProcessedMessagePort {
    Optional<ProcessedMessage> findByMessageIdAndConsumerName(String messageId, String consumerName);

    ProcessedMessage save(ProcessedMessage m);
}
