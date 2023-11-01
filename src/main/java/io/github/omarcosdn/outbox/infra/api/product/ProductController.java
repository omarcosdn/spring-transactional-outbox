package io.github.omarcosdn.outbox.infra.api.product;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.omarcosdn.outbox.app.usecases.CreateProductUseCase;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "product")
public class ProductController {

  private final CreateProductUseCase createUseCase;

  public ProductController(final CreateProductUseCase createUseCase) {
    this.createUseCase = Objects.requireNonNull(createUseCase);
  }

  @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<UUID> create(@RequestBody CreateRequest request) {
    final var name = request.name();
    final var command = new CreateProductUseCase.Command(name);
    final var output = createUseCase.execute(command);
    final var uri = URI.create("/product/".concat(output.toString()));
    return ResponseEntity.created(uri).body(output);
  }

  public record CreateRequest(@JsonProperty("name") String name) {}
}
