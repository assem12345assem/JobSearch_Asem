package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByEmployerId(Long employerId);
    Page<Vacancy> findAll(Pageable pageable);
    List<Vacancy> findAll();
    List<Vacancy> findByCategory(Category category, Sort sort);
}
