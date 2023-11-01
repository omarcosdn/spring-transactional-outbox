package io.github.omarcosdn.outbox.infra.product.persistence;

import io.github.omarcosdn.outbox.app.entities.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "product")
@Table(name = "product")
public class ProductJpaEntity {

  @Id
  @Column(name = "product_id")
  private UUID productId;

  @Column(name = "name")
  private String name;

  private ProductJpaEntity(final UUID productId, final String name) {
    this.productId = productId;
    this.name = name;
  }

  public static ProductJpaEntity from(final Product product) {
    return new ProductJpaEntity(product.getId(), product.getName());
  }

  public Product toAggregate() {
    return Product.restore(getProductId(), getName());
  }
}
