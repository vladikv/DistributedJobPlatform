package com.distributedjobplatform.distributedjobplatform.dto;

public record CreateJobRequest(
        String type,
        String payload
) {
}