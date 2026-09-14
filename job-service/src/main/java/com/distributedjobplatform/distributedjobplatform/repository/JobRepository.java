package com.distributedjobplatform.distributedjobplatform.repository;

import com.distributedjobplatform.distributedjobplatform.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
}