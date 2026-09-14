package com.distributedjobplatform.distributedjobplatform.service;

import com.distributedjobplatform.distributedjobplatform.event.JobCreatedEvent;
import com.distributedjobplatform.distributedjobplatform.event.JobEventProducer;
import com.distributedjobplatform.distributedjobplatform.exception.JobNotFoundException;
import com.distributedjobplatform.distributedjobplatform.model.Job;
import com.distributedjobplatform.distributedjobplatform.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobEventProducer jobEventProducer;

    public JobService(JobRepository jobRepository, JobEventProducer jobEventProducer) {
        this.jobRepository = jobRepository;
        this.jobEventProducer = jobEventProducer;
    }

    public Job createJob(String type, String payload) {
        Job job = new Job(type, payload);
        Job savedJob = jobRepository.save(job);

        jobEventProducer.publish(new JobCreatedEvent(savedJob.getId(), savedJob.getType(), savedJob.getPayload()));

        return savedJob;
    }

    public Job getJobById(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}