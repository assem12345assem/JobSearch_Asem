package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicant;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Component
public class ApplicantDao extends BaseDao implements CrudDao<Applicant, Long>{
    public ApplicantDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
    public void createAtRegister(String email) {
        String sql = "INSERT INTO applicants(user_id, first_name, last_name, date_of_birth) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, email, null, null, null);
    }
    @Override
    public Long save(Applicant applicant) {
        String sql = "INSERT INTO applicants(user_id, first_name, last_name, date_of_birth) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, applicant.getUserId());
            ps.setString(2, applicant.getFirstName());
            ps.setString(3, applicant.getLastName());
            ps.setTimestamp(4, Timestamp.valueOf(applicant.getDateOfBirth().atStartOfDay()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();

    }

    @Override
    public Optional<Applicant> find(Long id) {
        String sql = "SELECT * FROM applicants WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(Applicant.class), id)));

    }

    @Override
    public void update(Applicant obj) {
        String sql = "UPDATE applicants SET first_name = ?, last_name = ?, date_of_birth = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                obj.getFirstName(),
                obj.getLastName(),
                obj.getDateOfBirth(),
                obj.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from applicants where id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Optional<Applicant> findByEmail(String email) {
        String sql = """
                SELECT * FROM applicants WHERE user_id = ?
                """;

        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Applicant.class), email)));
    }
}
