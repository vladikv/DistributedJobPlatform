package com.distributedjobplatform.distributedjobplatform.metrics;

import com.distributedjobplatform.distributedjobplatform.model.JobStatus;
import com.distributedjobplatform.distributedjobplatform.repository.JobRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JobMetrics {

    private final JobRepository jobRepository;
    private final MeterRegistry meterRegistry;

    public JobMetrics(JobRepository jobRepository, MeterRegistry meterRegistry) {
        this.jobRepository = jobRepository;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerGauges() {
        for (JobStatus status : JobStatus.values()) {
            Gauge.builder("jobs_status_count", () -> jobRepository.countByStatus(status))
                    .tag("status", status.name())
                    .description("Current number of jobs in this status")
                    .register(meterRegistry);
        }
    }
}