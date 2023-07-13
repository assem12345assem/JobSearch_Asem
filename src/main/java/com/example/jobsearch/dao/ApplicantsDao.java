package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicantsDao {
    private final JdbcTemplate jdbcTemplate;
//    public List<Applicant> getAllApplicants() {
//        String sql = "select * from applicants";
//        return jdbcTemplate.query(sql, new ApplicantsMapper());
//    }
//    public List<Applicant> getAllApplicantsByVacancyId(int vacancyId) {
//        String sql = "select * from applicants where vacancyId = ?";
//        return jdbcTemplate.query(sql, new ApplicantsMapper(), vacancyId);
//    }
//    public List<Applicant> getAllVacanciesByApplicantId(int applicantUserId) {
//        String sql = "select * from applicants where applicantUserId = ?";
//        return jdbcTemplate.query(sql, new ApplicantsMapper(), applicantUserId);
//    }
}
