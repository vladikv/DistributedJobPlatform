package com.distributedjobplatform.distributedjobplatform.controller;

import com.distributedjobplatform.distributedjobplatform.dto.CreateJobRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody CreateJobRequest request) {
        return ResponseEntity.ok("Job received: type=" + request.type());
    }
}