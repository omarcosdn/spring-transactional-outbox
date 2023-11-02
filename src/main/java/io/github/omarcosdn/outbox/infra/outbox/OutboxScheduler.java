package io.github.omarcosdn.outbox.infra.outbox;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OutboxScheduler {

  private final OutboxService outboxService;

  public OutboxScheduler(final OutboxService outboxService) {
    this.outboxService = Objects.requireNonNull(outboxService);
  }

  @Scheduled(fixedRate = 10L, timeUnit = TimeUnit.SECONDS, initialDelay = 10L)
  public void sendPendingMessages() {
    outboxService.sendTopPendingMessages();
  }

  @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.MINUTES, initialDelay = 1L)
  public void deleteSentMessages() {
    outboxService.deleteSentMessages();
  }
}
