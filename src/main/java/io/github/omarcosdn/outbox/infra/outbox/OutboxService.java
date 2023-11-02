package io.github.omarcosdn.outbox.infra.outbox;

import io.github.omarcosdn.outbox.infra.messaging.MessageSender;
import io.github.omarcosdn.outbox.infra.outbox.persistence.OutboxJpaEntity;
import io.github.omarcosdn.outbox.infra.outbox.persistence.OutboxJpaEntity.Status;
import io.github.omarcosdn.outbox.infra.outbox.persistence.OutboxRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxService {
  private static final int MAX_TENTATIVES = 20;
  private final OutboxRepository repository;
  private final MessageSender messageSender;

  public OutboxService(final OutboxRepository repository, final MessageSender messageSender) {
    this.repository = Objects.requireNonNull(repository);
    this.messageSender = Objects.requireNonNull(messageSender);
  }

  @Transactional
  public void create(final UUID aggregateId, final String aggregateName, final String eventType, final String payloadJson, final LocalDateTime createdAt) {
    final var entity = OutboxJpaEntity.create(aggregateId, aggregateName, eventType, payloadJson, createdAt);
    save(entity);
  }

  @Transactional
  public void sendTopPendingMessages() {
    final var pendingMessages = repository.findTopTenMessagesByStatus(Status.PENDING.name());

    for (var message : pendingMessages) {
      message.increaseTentatives();
      try {
        messageSender.send(message.getEventType(), message.getPayload());
        message.markAsSent();
      } catch (Exception exception) {
        if (message.getTentatives() >= MAX_TENTATIVES) {
          message.markAsFailed();
        }
      } finally {
        save(message);
      }
    }
  }

  @Transactional
  public void deleteSentMessages() {
    while (true) {
      final var sentMessages = repository.findTopTenMessagesByStatus(Status.SENT.name());
      if (sentMessages.isEmpty()) {
        break;
      }
      repository.deleteAll(sentMessages);
    }
  }

  private void save(final OutboxJpaEntity entity) {
    repository.save(entity);
  }
}
