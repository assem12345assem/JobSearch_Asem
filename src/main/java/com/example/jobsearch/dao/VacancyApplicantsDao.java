package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.model.VacancyApplicants;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyApplicantsDao {
    private final JdbcTemplate jdbcTemplate;
    public List<VacancyApplicants> getAllApplicants() {
        String sql = "select * from applicants";
        return jdbcTemplate.query(sql, new VacancyApplicantsMapper());
    }
    public List<VacancyApplicants> getAllApplicantsByVacancyId(int vacancyId) {
        String sql = "select * from applicants where vacancyId = ?";
        return jdbcTemplate.query(sql, new VacancyApplicantsMapper(), vacancyId);
    }
}
