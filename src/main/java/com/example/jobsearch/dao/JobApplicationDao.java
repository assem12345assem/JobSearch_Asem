package com.example.jobsearch.dao;

import com.example.jobsearch.model.JobApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobApplicationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<JobApplication> getAllJobApplications() {
        String sql ="select * from JOBAPPLICATIONS";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobApplication.class));
    }
    public List<JobApplication> getAllJobApplicationsByResumeId(long resumeId) {
        String sql ="select * from JOBAPPLICATIONS where RESUMEID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobApplication.class), resumeId);

    }

}
