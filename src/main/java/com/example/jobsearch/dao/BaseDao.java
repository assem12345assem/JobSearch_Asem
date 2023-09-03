package com.example.jobsearch.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class BaseDao {
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final KeyHolder keyHolder;

    protected BaseDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }


}

