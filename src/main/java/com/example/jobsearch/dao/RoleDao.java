package com.example.jobsearch.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;

@Component
public class RoleDao extends BaseDao{
    public RoleDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        String sql = "insert into ROLES (ROLE, USER_EMAIL) values ( ?, ? )";

        jdbcTemplate.update(con -> {
            Role r = (Role) obj;
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, r.getRole());
            ps.setString(2, r.getUser_email());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from roles where id = ?";
        jdbcTemplate.update(sql, id);
    }

}
