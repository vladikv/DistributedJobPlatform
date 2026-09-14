package com.distributedjobplatform.distributedjobplatform.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobEventProducer {

    private static final String TOPIC = "jobs.created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public JobEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(JobCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event.jobId().toString(), event);
    }
}