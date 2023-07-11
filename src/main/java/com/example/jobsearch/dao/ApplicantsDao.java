package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicants;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicantsDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Applicants> getAllApplicants() {
        String sql = "select * from applicants";
        return jdbcTemplate.query(sql, new ApplicantsMapper());
    }
    public List<Applicants> getAllApplicantsByVacancyId(int vacancyId) {
        String sql = "select * from applicants where vacancyId = ?";
        return jdbcTemplate.query(sql, new ApplicantsMapper(), vacancyId);
    }
    public List<Applicants> getAllVacanciesByApplicantId(int applicantUserId) {
        String sql = "select * from applicants where applicantUserId = ?";
        return jdbcTemplate.query(sql, new ApplicantsMapper(), applicantUserId);
    }
}
