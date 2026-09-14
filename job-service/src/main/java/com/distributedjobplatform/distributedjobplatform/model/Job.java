package com.distributedjobplatform.distributedjobplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String type;

    private String payload;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private Instant createdAt;

    public Job() {
    }

    public Job(String type, String payload) {
        this.type = type;
        this.payload = payload;
        this.status = JobStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
