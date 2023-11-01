package io.github.omarcosdn.outbox.app.entities.product;

import io.github.omarcosdn.outbox.app.entities.Aggregate;
import io.github.omarcosdn.outbox.app.exceptions.DomainException;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public final class Product extends Aggregate {

  private String name;

  private Product(final UUID id, final String name) {
    super(id);
    this.name = name;
  }

  public static Product create(final String name) {
    final var productId = UUID.randomUUID();
    final var product = new Product(productId, name);
    product.validate();
    product.registerCreatedEvent();
    return product;
  }

  public static Product restore(final UUID productId, final String name) {
    final var product = new Product(productId, name);
    product.validate();
    return product;
  }

  public void update(final String name) {
    this.name = name;
    this.validate();
  }

  private void registerCreatedEvent() {
    this.registerEvent(new ProductCreated(this.id));
  }

  private void validate() {
    if (this.name == null || this.name.isEmpty()) {
      throw new DomainException("'Name' should not be null or empty");
    }
  }

  @Override
  public String getAggregateName() {
    return getClass().getSimpleName();
  }
}
