package com.distributedjobplatform.distributedjobplatform.event;

import com.distributedjobplatform.distributedjobplatform.exception.EventPublishException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JobEventProducer {

    private static final Logger log = LoggerFactory.getLogger(JobEventProducer.class);

    private static final String TOPIC = "jobs.created";
    private static final long ACK_TIMEOUT_SECONDS = 5;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Retry retry;
    private final CircuitBreaker circuitBreaker;

    public JobEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                            RetryRegistry retryRegistry,
                            CircuitBreakerRegistry circuitBreakerRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.retry = retryRegistry.retry("kafkaPublish");
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("kafkaPublish");

        this.retry.getEventPublisher().onRetry(e ->
                log.warn("Kafka publish failed, retry attempt {}: {}",
                        e.getNumberOfRetryAttempts(), e.getLastThrowable().getMessage()));
        this.circuitBreaker.getEventPublisher().onStateTransition(e ->
                log.warn("Circuit breaker '{}' state change: {}",
                        e.getCircuitBreakerName(), e.getStateTransition()));
    }

    public void publish(JobCreatedEvent event) {
        try {
            retry.executeRunnable(() -> circuitBreaker.executeRunnable(() -> send(event)));
        } catch (CallNotPermittedException e) {
            throw new EventPublishException(event.jobId(), e);
        }
    }

    private void send(JobCreatedEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event.jobId().toString(), event)
                    .get(ACK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EventPublishException(event.jobId(), e);
        } catch (Exception e) {
            throw new EventPublishException(event.jobId(), e);
        }
    }
}