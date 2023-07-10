package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactInfo;
import com.example.jobsearch.model.JobseekerResume;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JobseekerResumeDao {
    private final JdbcTemplate jdbcTemplate;
    public List<JobseekerResume> getAllJobseekerResumes() {
        String sql = "select * from JOBSEEKERRESUMES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobseekerResume.class));
    }
    public List<JobseekerResume> getAllJobseekerResumesByUserId(int userId) {
        String sql = "select * from JOBSEEKERRESUMES where userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobseekerResume.class), userId);
    }




}
