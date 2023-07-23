package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Component

public class ApplicantDao  extends BaseDao{
    public ApplicantDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        Applicant e = (Applicant) obj;
        String sql = "insert into applicants (userid, firstname, lastname, dateofbirth) \n" +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, e.getUserId());
            ps.setString(2, e.getFirstName());
            ps.setString(3, e.getLastName());
            ps.setTimestamp(4,  Timestamp.valueOf(e.getDateOfBirth().atStartOfDay()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from applicants where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Applicant> getAllApplicants() {
        String sql = "select * from applicants";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Applicant.class));
    }
    public Applicant getApplicantById(long id) {
        String sql = "select * from applicants where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Applicant.class), id);
    }
    public Applicant getApplicantByUserId(String userId) {
        String sql = "select * from applicants where userId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Applicant.class), userId);
    }
    public Applicant getApplicantByFirstName(String firstName) {
        String sql = "select * from applicants where firstName = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Applicant.class), firstName);
    }
    public Applicant getApplicantByLastName(String lastName) {
        String sql = "select * from applicants where lastName = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Applicant.class), lastName);
    }
    public void editApplicant(Applicant e) {
        String sql = """
                update APPLICANTS
                set USERID = ?, FIRSTNAME = ?, LASTNAME = ?, DATEOFBIRTH = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getUserId(), e.getFirstName(), e.getLastName(), e.getDateOfBirth(), e.getId());
    }

    public boolean ifApplicantExists(String userId) {
        String sql = "select * from APPLICANTS where USERID = ?";
        Applicant applicant = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Applicant.class), userId);
        return applicant != null;
    }
}
