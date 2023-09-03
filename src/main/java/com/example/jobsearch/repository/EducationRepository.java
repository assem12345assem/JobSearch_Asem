package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByResumeId(Long id);
}
