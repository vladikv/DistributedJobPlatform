package com.distributedjobplatform.workerservice.service;

import com.distributedjobplatform.workerservice.model.Job;
import com.distributedjobplatform.workerservice.model.JobStatus;
import com.distributedjobplatform.workerservice.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobProcessingService {

    private final JobRepository jobRepository;

    public JobProcessingService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void process(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalStateException("Job not found: " + jobId));

        job.setStatus(JobStatus.RUNNING);
        jobRepository.save(job);

        try {
            simulateWork(job);
            job.setStatus(JobStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println("Job processing failed for id=" + jobId + ": " + e.getMessage());
            job.setStatus(JobStatus.FAILED);
        }

        jobRepository.save(job);
    }

    private void simulateWork(Job job) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while processing job", e);
        }

        if ("fail".equalsIgnoreCase(job.getPayload())) {
            throw new RuntimeException("Simulated processing failure");
        }
    }
}