package io.github.omarcosdn.outbox.app.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface DomainEvent extends Serializable {

  String eventType();
  LocalDateTime createdAt();
}
