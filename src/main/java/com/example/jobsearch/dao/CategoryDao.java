package com.example.jobsearch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
   private final JdbcTemplate jdbcTemplate;
    public Optional<String> getCategory (String categoryName) {
        String sql = "select * from categories where category = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(String.class), categoryName)));
    }
    public void save(String category) {
        String sql = "insert into CATEGORIES (CATEGORY) values (  )";
        jdbcTemplate.update(sql, new BeanPropertyRowMapper<>(String.class), category);
    }
}
