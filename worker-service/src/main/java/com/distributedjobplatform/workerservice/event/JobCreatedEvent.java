package com.distributedjobplatform.workerservice.event;

import java.util.UUID;

public record JobCreatedEvent(
    UUID id,
    String type,
    String payload
) {
}
