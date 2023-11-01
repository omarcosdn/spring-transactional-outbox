package io.github.omarcosdn.outbox.infra.product;

import io.github.omarcosdn.outbox.app.entities.product.Product;
import io.github.omarcosdn.outbox.app.events.DomainEvent;
import io.github.omarcosdn.outbox.app.repositories.ProductRepository;
import io.github.omarcosdn.outbox.infra.ObjectMapperHolder;
import io.github.omarcosdn.outbox.infra.messaging.product.ProductEvent;
import io.github.omarcosdn.outbox.infra.outbox.OutboxService;
import io.github.omarcosdn.outbox.infra.product.persistence.ProductJpaEntity;
import io.github.omarcosdn.outbox.infra.product.persistence.ProductJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository repository;
  private final OutboxService outboxService;

  public ProductRepositoryImpl(final ProductJpaRepository repository, final OutboxService outboxService) {
    this.repository = Objects.requireNonNull(repository);
    this.outboxService = Objects.requireNonNull(outboxService);
  }

  public Product create(final Product product) {
    return save(product);
  }

  private Product save(final Product product) {
    final var entity = ProductJpaEntity.from(product);
    product.publishDomainEvents(domainEvent -> createOutbox(product, domainEvent));
    return repository.save(entity).toAggregate();
  }

  private void createOutbox(final Product product, final DomainEvent domainEvent) {
    final var event = new ProductEvent(product.getId());
    final var json = ObjectMapperHolder.writeValueAsString(event);
    final var aggregateId = product.getId();
    outboxService.create(aggregateId, product.getAggregateName(), domainEvent.eventType(), json, domainEvent.createdAt());
  }
}
