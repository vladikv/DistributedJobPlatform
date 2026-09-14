package com.distributedjobplatform.distributedjobplatform.controller;

import com.distributedjobplatform.distributedjobplatform.dto.CreateJobRequest;
import com.distributedjobplatform.distributedjobplatform.dto.JobResponse;
import com.distributedjobplatform.distributedjobplatform.model.Job;
import com.distributedjobplatform.distributedjobplatform.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable UUID id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(JobResponse.from(job));
    }
}