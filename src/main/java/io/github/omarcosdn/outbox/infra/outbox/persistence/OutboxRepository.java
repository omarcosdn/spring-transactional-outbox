package io.github.omarcosdn.outbox.infra.outbox.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutboxRepository extends JpaRepository<OutboxJpaEntity, UUID> {

  @Query(value = "SELECT * FROM outbox WHERE status = :status ORDER BY created_at ASC LIMIT 10 FOR UPDATE SKIP LOCKED", nativeQuery = true)
  List<OutboxJpaEntity> findTopTenMessagesByStatus(@Param("status") String status);
}
