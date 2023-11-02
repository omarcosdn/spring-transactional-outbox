package io.github.omarcosdn.outbox.infra.messaging.rabbit;

import io.github.omarcosdn.outbox.infra.messaging.MessageSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

  public static final String APP_EXCHANGE = "app.events";
  public static final String APP_CREATED_RK = "app.product.created";
  public static final String PRODUCT_NOTIFICATION_QUEUE = "app.product-notification.queue";

  @Bean("RabbitOutboxMessageSender")
  public MessageSender messageSender(final RabbitTemplate rabbitTemplate, final RoutingKeyHandler routingKeyHandler) {
    return new RabbitOutboxMessageSender(rabbitTemplate, routingKeyHandler);
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    return factory;
  }

  @Bean
  public CachingConnectionFactory connectionFactory() {
    final var connectionFactory = new CachingConnectionFactory("localhost");
    connectionFactory.setUsername("dev");
    connectionFactory.setPassword("dev");
    return connectionFactory;
  }

  @Bean
  public RabbitAdmin rabbitAdmin() {
    return new RabbitAdmin(connectionFactory());
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    return new RabbitTemplate(connectionFactory());
  }

  @Configuration
  static class QueueConfig {
    private static final boolean DURABLE = true;
    private static final boolean EXCLUSIVE = false;
    private static final boolean AUTO_DELETE = false;

    @Bean(name = "AppEventsExchange")
    public DirectExchange appEventsExchange() {
      return new DirectExchange(APP_EXCHANGE);
    }

    @Bean(name = "ProductNotificationQueue")
    public Queue productNotificationQueue() {
      return new Queue(PRODUCT_NOTIFICATION_QUEUE, DURABLE, EXCLUSIVE, AUTO_DELETE);
    }

    @Bean(name = "ProductNotificationQueueBinding")
    public Binding productNotificationQueueBinding() {
      return BindingBuilder.bind(productNotificationQueue())
          .to(appEventsExchange())
          .with(APP_CREATED_RK);
    }
  }
}
