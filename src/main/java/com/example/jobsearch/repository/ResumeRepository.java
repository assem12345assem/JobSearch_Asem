package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Resume;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByApplicantId(Long applicantId);

    List<Resume> findByCategory(Category category, Sort by);
@Query("""
        SELECT r
        FROM Resume r
        WHERE (:category IS NULL OR r.category = :category)
        AND (
            :searchWord IS NULL
            OR LOWER(r.resumeTitle) LIKE CONCAT('%', LOWER(:searchWord), '%')
            OR LOWER(r.category) LIKE CONCAT('%', LOWER(:searchWord), '%')
            OR CAST(r.expectedSalary as STRING ) LIKE CONCAT('%', LOWER(:searchWord), '%')
        )
        """)
List<Resume> findByCategoryAndSearchWord(Category category, String searchWord, Sort sort);
}
