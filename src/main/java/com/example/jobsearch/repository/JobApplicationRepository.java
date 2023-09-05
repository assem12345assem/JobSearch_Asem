package com.example.jobsearch.repository;

import com.example.jobsearch.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findAllByResumeId(Long id);

    List<JobApplication> findAllByVacancyId(Long id);

    @Query("SELECT COUNT(ja) FROM JobApplication ja GROUP BY ja.vacancy.id")
    List<Integer> getCountByVacancy();
}
