package io.github.omarcosdn.outbox.infra.messaging.rabbit;

import io.github.omarcosdn.outbox.infra.messaging.MessageSender;
import java.util.Objects;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public final class RabbitOutboxMessageSender implements MessageSender {

  private final RabbitTemplate rabbitTemplate;
  private final RoutingKeyHandler routingKeyHandler;

  public RabbitOutboxMessageSender(final RabbitTemplate rabbitTemplate,
                                   final RoutingKeyHandler routingKeyHandler) {
    this.rabbitTemplate = Objects.requireNonNull(rabbitTemplate);
    this.routingKeyHandler = Objects.requireNonNull(routingKeyHandler);
  }

  @Override
  public void send(final String eventType, final String payload) {
    final var routingKey = routingKeyHandler.get(eventType);
    this.rabbitTemplate.convertAndSend(routingKey.exchange(), routingKey.routingKey(), payload.getBytes());
  }
}
