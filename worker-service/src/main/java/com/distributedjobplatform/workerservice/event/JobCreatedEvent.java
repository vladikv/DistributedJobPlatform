package com.distributedjobplatform.workerservice.event;

import java.util.UUID;

public record JobCreatedEvent(
    UUID jobId,
    String type,
    String payload
) {
}
