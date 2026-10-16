package com.distributedjobplatform.distributedjobplatform.exception;

import java.util.UUID;

public class EventPublishException extends RuntimeException {

    public EventPublishException(UUID jobId, Throwable cause) {
        super("Failed to publish event for job " + jobId, cause);
    }
}