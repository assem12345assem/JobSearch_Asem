package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Vacancy> getAllVacancies() {
        String sql = "select * from VACANCIES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }
    public List<Vacancy> getAllEmployerVacanciesByUserId(String userId) {
        String sql = "select * from VACANCIES where EMPLOYERID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }
    public List<Vacancy> getAllVacanciesByCategory(Long categoryId) {
        String sql = "select * from VACANCIES where categoryId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
    }
    public Vacancy getVacancyById(Long id) {
        String sql = "select * from VACANCIES where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);

    }

    public List<Vacancy> getVacancyListByIdList(List<Long> id) {
        List<Vacancy> list = new ArrayList<>();
        id.forEach(e -> list.add(getVacancyById(e)));
        return list;
    }
}

