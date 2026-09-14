package com.distributedjobplatform.distributedjobplatform.service;

import com.distributedjobplatform.distributedjobplatform.model.Job;
import com.distributedjobplatform.distributedjobplatform.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(String type, String payload) {
        Job job = new Job(type, payload);
        return jobRepository.save(job);
    }
}