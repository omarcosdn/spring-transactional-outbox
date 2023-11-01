package io.github.omarcosdn.outbox.app.entities;

import io.github.omarcosdn.outbox.app.events.DomainEvent;
import io.github.omarcosdn.outbox.app.events.DomainEventPublisher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class Aggregate {

  @Getter protected final UUID id;

  @EqualsAndHashCode.Exclude private final List<DomainEvent> domainEvents;

  protected Aggregate(final UUID id) {
    this(id, null);
  }

  protected Aggregate(final UUID id, final List<DomainEvent> domainEvents) {
    this.id = Objects.requireNonNull(id, "'Id' should not be null");
    this.domainEvents = new ArrayList<>(domainEvents == null ? Collections.emptyList() : domainEvents);
  }

  public List<DomainEvent> getDomainEvents() {
    return Collections.unmodifiableList(this.domainEvents);
  }

  public void publishDomainEvents(final DomainEventPublisher publisher) {
    if (publisher == null) {
      return;
    }
    for (var event : getDomainEvents()) {
      publisher.publishEvent(event);
    }
    this.domainEvents.clear();
  }

  public void registerEvent(final DomainEvent domainEvent) {
    if (domainEvent == null) {
      return;
    }
    this.domainEvents.add(domainEvent);
  }

  public abstract String getAggregateName();
}
