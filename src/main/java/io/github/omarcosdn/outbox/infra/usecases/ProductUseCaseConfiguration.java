package io.github.omarcosdn.outbox.infra.usecases;

import io.github.omarcosdn.outbox.app.repositories.ProductRepository;
import io.github.omarcosdn.outbox.app.usecases.CreateProductUseCase;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfiguration {

  private final ProductRepository repository;

  public ProductUseCaseConfiguration(final ProductRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  @Bean
  public CreateProductUseCase createUseCase() {
    return new CreateProductUseCase(repository);
  }
}
