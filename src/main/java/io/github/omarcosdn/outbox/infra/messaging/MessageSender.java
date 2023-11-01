package io.github.omarcosdn.outbox.infra.messaging;

public interface MessageSender {

  void send(String aggregateType, String eventType, String payload);
}
