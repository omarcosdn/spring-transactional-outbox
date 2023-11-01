package io.github.omarcosdn.outbox.app.entities.product;

import io.github.omarcosdn.outbox.app.events.DomainEvent;
import io.github.omarcosdn.outbox.app.events.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductCreated(UUID productId, String eventType, LocalDateTime createdAt)
    implements DomainEvent {

  public ProductCreated(final UUID productId) {
    this(productId, EventType.PRODUCT_CREATED, LocalDateTime.now());
  }
}
