package com.example.jobsearch.dao;

import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    public List<WorkExperience> getAllWorkExperienceByResumeId(int resumeId) {
        String sql = "select * from WORKEXPERIENCE where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkExperience.class), resumeId);
    }
}
