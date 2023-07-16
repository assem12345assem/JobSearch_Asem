package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicantDao {
    private final JdbcTemplate jdbcTemplate;
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
    public void createApplicant(Applicant e) {
        String sql = "insert into APPLICANTS (USERID, FIRSTNAME, LASTNAME, DATEOFBIRTH) \n" +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, e.getUserId(), e.getFirstName(), e.getLastName(), e.getDateOfBirth());
    }
    public void deleteApplicant(Applicant e) {
        String sql = "delete from APPLICANTS where id = ?";
        jdbcTemplate.update(sql, e.getId());
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
