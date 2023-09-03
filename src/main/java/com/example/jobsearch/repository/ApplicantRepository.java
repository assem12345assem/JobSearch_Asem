package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByUserEmail(String email);
}
