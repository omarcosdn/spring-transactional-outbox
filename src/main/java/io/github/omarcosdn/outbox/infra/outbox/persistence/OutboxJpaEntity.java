package io.github.omarcosdn.outbox.infra.outbox.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Outbox")
@Table(name = "outbox")
public class OutboxJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "aggregate_id")
  private UUID aggregateId;

  @Column(name = "aggregate_type")
  private String aggregateType;

  @Column(name = "event_type")
  private String eventType;

  @Column(name = "payload")
  private String payload;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "tentatives")
  private int tentatives;

  private OutboxJpaEntity(final UUID aggregateId,
                          final String aggregateType,
                          final String eventType,
                          final String payload,
                          final LocalDateTime createdAt,
                          final Status status) {
    this.aggregateId = aggregateId;
    this.aggregateType = aggregateType;
    this.eventType = eventType;
    this.payload = payload;
    this.createdAt = createdAt;
    this.status = status;
    this.tentatives = 0;
  }

  public static OutboxJpaEntity create(final UUID aggregateId,
                                       final String aggregateType,
                                       final String eventType,
                                       final String payload,
                                       final LocalDateTime createdAt) {
    return new OutboxJpaEntity(aggregateId, aggregateType, eventType, payload, createdAt, Status.PENDING);
  }

  public void markAsSent() {
    this.status = Status.SENT;
  }

  public void markAsFailed() {
    this.status = Status.FAILED;
  }

  public void increaseTentatives() {
    this.tentatives++;
  }

  public enum Status {
    PENDING,
    SENT,
    FAILED;
  }
}
