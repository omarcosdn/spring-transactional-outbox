package io.github.omarcosdn.outbox.app.usecases;

import io.github.omarcosdn.outbox.app.entities.product.Product;
import io.github.omarcosdn.outbox.app.repositories.ProductRepository;
import java.util.Objects;
import java.util.UUID;

public final class CreateProductUseCase {

  private final ProductRepository repository;

  public CreateProductUseCase(final ProductRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  public UUID execute(final Command command) {
    final var product = Product.create(command.name());
    final var createdProduct = repository.create(product);
    return createdProduct.getId();
  }

  public record Command(String name) {}
}
