package com.meli.inventory_service.infrastructure.persistence;

import com.meli.inventory_service.domain.ports.out.OutboxPort;
import com.meli.inventory_service.domain.model.OutboxMessage;
import com.meli.inventory_service.infrastructure.persistence.spring.OutboxRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JpaOutboxAdapter implements OutboxPort {
    private final OutboxRepository repo;

    public JpaOutboxAdapter(OutboxRepository repo) {
        this.repo = repo;
    }

    @Override
    public OutboxMessage save(OutboxMessage m) {
        return repo.save(m);
    }

    @Override
    public List<OutboxMessage> fetchPending(int limit) {
        return repo.findTop100ByPublishedFalseOrderByCreatedAtAsc();
    }

    @Override
    public void markPublished(Long id) {
        var opt = repo.findById(id);
        opt.ifPresent(m -> {
            m.setPublished(true);
            m.setPublishedAt(java.time.Instant.now());
            repo.save(m);
        });
    }

    @Override
    public Optional<OutboxMessage> findById(Long id) {
        return repo.findById(id);
    }
}
