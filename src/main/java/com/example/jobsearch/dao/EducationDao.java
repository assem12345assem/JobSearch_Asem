package com.example.jobsearch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<String> getAllEducationByUserId(int userId) {
        String sql = "select EDUCATIONITEM from EDUCATION where userId = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }
}
