package de.maxknoll.app.repository;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.time.Instant;

@Entity
@Table(name = "sensordata")
public class SensordataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private Double temperature;

    public SensordataEntity() {}

    public SensordataEntity(Long userId, Instant timestamp, Double temperature) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
