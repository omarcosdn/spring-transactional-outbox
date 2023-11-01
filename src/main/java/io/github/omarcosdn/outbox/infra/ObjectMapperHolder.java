package io.github.omarcosdn.outbox.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectMapperHolder {
  private static final ObjectMapper MAPPER =
      new Jackson2ObjectMapperBuilder()
          .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
          .build();

  public static String writeValueAsString(final Object object) {
    return invoke(() -> MAPPER.writeValueAsString(object));
  }

  private static <T> T invoke(final Callable<T> callable) {
    try {
      return callable.call();
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
