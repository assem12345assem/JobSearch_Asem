package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Vacancy> getAllVacancies() {
        String sql = "select * from VACANCIES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }
    public List<Vacancy> getAllEmployerVacanciesByUserId(int userId) {
        String sql = "select * from VACANCIES where userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }
}

