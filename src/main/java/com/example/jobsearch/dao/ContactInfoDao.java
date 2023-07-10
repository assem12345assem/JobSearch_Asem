package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactInfoDao {
    private final JdbcTemplate jdbcTemplate;
    public ContactInfo getAllContactInfoByUserId(int userId) {
        String sql = "select * from CONTACTINFO where userId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactInfo.class), userId);
    }
}
