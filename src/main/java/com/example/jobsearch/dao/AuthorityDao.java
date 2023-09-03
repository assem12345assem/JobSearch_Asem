package com.example.jobsearch.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorityDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(String authority, String email) {
        String sql = "insert into authorities(authority, email) values (?, ?)";
        jdbcTemplate.update(sql, authority, email);
    }
}
