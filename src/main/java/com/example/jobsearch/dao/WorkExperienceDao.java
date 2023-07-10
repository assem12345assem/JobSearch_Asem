package com.example.jobsearch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    public List<String> getAllWorkExperienceByUserId(int userId) {
        String sql = "select WORKEXPERIENCEITEM from WORKEXPERIENCE where userId = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }
}
