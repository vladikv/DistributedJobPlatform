package com.distributedjobplatform.distributedjobplatform.event;

import java.util.UUID;

public record JobCreatedEvent(
        UUID jobId,
        String type,
        String payload
) {
}