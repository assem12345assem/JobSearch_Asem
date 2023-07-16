package com.example.jobsearch.dao;

import com.example.jobsearch.model.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployerDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Employer> getAllEmployers() {
        String sql = "select * from employers";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class));
    }
    public Employer getEmployerByUserId(String email) {
        String sql = "select * from employers where userId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employer.class), email);

    }
    public void createEmployer(Employer employer) {
        String sql = "insert into EMPLOYERS (USERID, COMPANYNAME) VALUES ( ?, ? )";
        jdbcTemplate.update(sql, employer.getUserId(), employer.getCompanyName());
    }
    public boolean ifEmployerExists(String userEmail) {
        String sql = "select id from EMPLOYERS where USERID = ?";
        Long l = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Long.class), userEmail);
        return l != null;
    }

    public void editEmployer(Employer employer) {
        String sql = "update EMPLOYERS\n" +
                "set COMPANYNAME = ?\n" +
                "where ID = ?";
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
