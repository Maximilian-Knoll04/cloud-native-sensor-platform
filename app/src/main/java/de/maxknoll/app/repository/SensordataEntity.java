package de.maxknoll.app.repository;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sensordata")
public class SensordataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private Double temperature;

    public SensordataEntity() {}

    public SensordataEntity(UUID userId, Instant timestamp, Double temperature) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
