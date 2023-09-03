package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
