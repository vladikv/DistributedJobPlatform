package com.distributedjobplatform.distributedjobplatform.controller;

import com.distributedjobplatform.distributedjobplatform.dto.CreateJobRequest;
import com.distributedjobplatform.distributedjobplatform.dto.JobResponse;
import com.distributedjobplatform.distributedjobplatform.model.Job;
import com.distributedjobplatform.distributedjobplatform.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestBody CreateJobRequest request) {
        Job job = jobService.createJob(request.type(), request.payload());
        return ResponseEntity.ok(JobResponse.from(job));
    }
}