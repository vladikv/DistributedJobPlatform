package com.distributedjobplatform.distributedjobplatform.dto;

import com.distributedjobplatform.distributedjobplatform.model.Job;
import com.distributedjobplatform.distributedjobplatform.model.JobStatus;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID id,
        String type,
        JobStatus status,
        Instant createdAt
) {
    public static JobResponse from(Job job) {
        return new JobResponse(job.getId(), job.getType(), job.getStatus(), job.getCreatedAt());
    }
}