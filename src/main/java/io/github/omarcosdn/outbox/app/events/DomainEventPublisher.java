package io.github.omarcosdn.outbox.app.events;

@FunctionalInterface
public interface DomainEventPublisher {

  void publishEvent(DomainEvent event);
}
