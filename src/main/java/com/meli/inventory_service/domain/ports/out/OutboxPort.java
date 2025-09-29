package com.meli.inventory_service.domain.ports.out;

import com.meli.inventory_service.domain.model.OutboxMessage;
import java.util.List;
import java.util.Optional;

public interface OutboxPort {
    OutboxMessage save(OutboxMessage m);

    List<OutboxMessage> fetchPending(int limit);

    void markPublished(Long id);

    Optional<OutboxMessage> findById(Long id);
}
