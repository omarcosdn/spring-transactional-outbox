package io.github.omarcosdn.outbox.infra.messaging.rabbit.listeners;

import static io.github.omarcosdn.outbox.infra.messaging.rabbit.RabbitConfiguration.PRODUCT_NOTIFICATION_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public final class ProductNotificationListener {

  private static final Logger logger = LoggerFactory.getLogger(ProductNotificationListener.class);

  @RabbitListener(id = "ProductNotificationListener", queues = PRODUCT_NOTIFICATION_QUEUE)
  public void onMessage(final String message) {
    logger.info("Product: {}", message);
  }
}
