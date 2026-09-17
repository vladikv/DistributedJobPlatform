package com.distributedjobplatform.workerservice.event;

import com.distributedjobplatform.workerservice.service.JobProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobEventConsumer {

    private final JobProcessingService jobProcessingService;

    public JobEventConsumer(JobProcessingService jobProcessingService) {
        this.jobProcessingService = jobProcessingService;
    }

    @KafkaListener(topics = "jobs.created", groupId = "worker-service")
    public void consume(JobCreatedEvent event) {
        System.out.println("Received job event: " + event);
        jobProcessingService.process(event.jobId());
    }
}