package com.distributedjobplatform.workerservice.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobEventConsumer {

    @KafkaListener(topics = "jobs.created", groupId = "worker-service")
    public void consume(JobCreatedEvent event) {
        System.out.println("Received job event: " + event);
    }
}