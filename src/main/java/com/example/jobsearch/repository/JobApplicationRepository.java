package com.example.jobsearch.repository;

import com.example.jobsearch.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findAllByResumeId(Long id);

    List<JobApplication> findAllByVacancyId(Long id);
}
