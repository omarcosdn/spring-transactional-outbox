package io.github.omarcosdn.outbox.infra.messaging;

public interface MessageSender {

  void send(String eventType, String payload);
}
