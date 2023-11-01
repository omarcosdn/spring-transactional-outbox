package io.github.omarcosdn.outbox.app.repositories;

import io.github.omarcosdn.outbox.app.entities.product.Product;

public interface ProductRepository {

  Product create(Product product);
}
