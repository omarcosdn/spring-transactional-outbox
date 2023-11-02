package io.github.omarcosdn.outbox.infra.messaging.rabbit;

import static io.github.omarcosdn.outbox.infra.messaging.rabbit.RabbitConfiguration.APP_CREATED_RK;
import static io.github.omarcosdn.outbox.infra.messaging.rabbit.RabbitConfiguration.APP_EXCHANGE;

import io.github.omarcosdn.outbox.app.events.EventType;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public final class RoutingKeyHandler {
  private final Map<String, RoutingKey> eventMap = new HashMap<>();

  @PostConstruct
  private void init() {
    addEventRouting(EventType.PRODUCT_CREATED, APP_CREATED_RK);
  }

  private void addEventRouting(final String eventType, final String routingKey) {
    eventMap.put(eventType, new RoutingKey(APP_EXCHANGE, routingKey));
  }

  public RoutingKey get(final String eventType) {
    final var routingKey = eventMap.get(eventType);
    if (routingKey == null) {
      throw new IllegalArgumentException("Unknown event type: " + eventType);
    }
    return routingKey;
  }

  public record RoutingKey(String exchange, String routingKey) {}
}
