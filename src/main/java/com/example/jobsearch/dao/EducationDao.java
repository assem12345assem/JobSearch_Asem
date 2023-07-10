package com.example.jobsearch.dao;

import com.example.jobsearch.model.Education;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Education> getAllEducationByResumeId(int resumeId) {
        String sql = "select * from EDUCATION where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Education.class), resumeId);
    }
}
