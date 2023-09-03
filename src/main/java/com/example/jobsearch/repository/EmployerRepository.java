package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUserId(String userId);
}
