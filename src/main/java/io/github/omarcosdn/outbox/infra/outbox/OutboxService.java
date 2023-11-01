package io.github.omarcosdn.outbox.infra.outbox;

import io.github.omarcosdn.outbox.infra.outbox.persistence.OutboxJpaEntity;
import io.github.omarcosdn.outbox.infra.outbox.persistence.OutboxRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxService {

  private final OutboxRepository repository;

  public OutboxService(final OutboxRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  @Transactional
  public void create(final UUID aggregateId, final String aggregateName, final String eventType, final String payloadJson, final LocalDateTime createdAt) {
    final var entity = OutboxJpaEntity.create(aggregateId, aggregateName, eventType, payloadJson, createdAt);
    save(entity);
  }

  private void save(final OutboxJpaEntity entity) {
    repository.save(entity);
  }
}
