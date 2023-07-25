package com.example.jobsearch.dao;

import com.example.jobsearch.model.Employer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class EmployerDao extends BaseDao{
    public EmployerDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        Employer e = (Employer) obj;
        String sql = "insert into EMPLOYERS (USERID, COMPANYNAME) VALUES ( ?, ? )";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, e.getUserId());
            ps.setString(2, e.getCompanyName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from employers where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Employer> getAllEmployers() {
        String sql = "select * from employers";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class));
    }
    public Employer getEmployerByUserId(String email) {
        String sql = "select * from employers where userId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employer.class), email);

    }
    public String getUserIdByEmployerId(Long employerId) {
        String sql = "Select userid from employers where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(String.class), employerId);
    }
    public boolean ifEmployerExists(String userEmail) {
        String sql = "select id from EMPLOYERS where USERID = ?";
        Long l = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Long.class), userEmail);
        return l != null;
    }

    public void editEmployer(Employer employer) {
        String sql = """
                update EMPLOYERS
                set COMPANYNAME = ?
                where ID = ?""";
        jdbcTemplate.update(sql, employer.getCompanyName());
    }

    public Employer getEmployerById(Long id) {
        String sql = "select * from EMPLOYERS where ID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employer.class), id);
    }

    public List<Employer> getEmployerByCompanyName(String companyName) {
        String sql = "select * from EMPLOYERS where companyName like ?";
        companyName = "%" + companyName + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class), companyName);
    }
}
