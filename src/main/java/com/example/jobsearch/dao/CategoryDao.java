package com.example.jobsearch.dao;

import com.example.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    public Category getCategoryById(long id) {
        String sql = "select * from categories where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id);
    }

    public Long getIdByCategory(String category) {
        String sql = "select id from categories where category like ?";
        category = "%" + category + "%";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Long.class), category);

    }
}
