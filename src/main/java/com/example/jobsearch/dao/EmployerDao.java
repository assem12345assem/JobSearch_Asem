package com.example.jobsearch.dao;

import com.example.jobsearch.model.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployerDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Employer> getAllEmployers() {
        String sql = "select * from employers";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class));
    }
    public Employer getEmployerByUserId(String email) {
        String sql = "select * from employers where userId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employer.class), email);

    }
}
