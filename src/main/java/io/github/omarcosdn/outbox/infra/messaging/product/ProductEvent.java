package io.github.omarcosdn.outbox.infra.messaging.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ProductEvent(@JsonProperty("product_id") UUID productId) {}
