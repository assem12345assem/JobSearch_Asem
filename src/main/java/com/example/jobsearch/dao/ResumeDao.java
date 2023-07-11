package com.example.jobsearch.dao;

import com.example.jobsearch.enums.Category;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Resume> getAllResumes() {
        String sql = "select * from RESUMES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }
    public List<Resume> getAllResumesByUserId(int userId) {
        String sql = "select * from RESUMES where userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }
    public List<Resume> getAllResumesByCategory(Category category) {
        String sql = "select * from RESUMES where category = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), category);
    }



}
