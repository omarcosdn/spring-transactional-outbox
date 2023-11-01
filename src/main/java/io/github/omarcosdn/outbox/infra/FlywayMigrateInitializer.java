package io.github.omarcosdn.outbox.infra;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayMigrateInitializer {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @PostConstruct
  public void migrate() {
    final var fluentConfiguration =
        new FluentConfiguration()
            .defaultSchema("public")
            .dataSource(url, username, password)
            .schemas("public")
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .outOfOrder(false);
    final var flyway = new Flyway(fluentConfiguration);
    flyway.repair();
    flyway.migrate();
  }
}
