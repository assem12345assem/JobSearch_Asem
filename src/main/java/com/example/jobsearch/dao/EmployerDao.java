package com.example.jobsearch.dao;

import com.example.demo.entity.Employer;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Component
public class EmployerDao extends BaseDao implements CrudDao<Employer, Long> {
    public EmployerDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Employer employer) {
        String sql = "INSERT INTO employers(user_id, company_name) VALUES (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, employer.getUserId());
            ps.setString(2, employer.getCompanyName());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();

    }
    public void createAtRegister(String email) {
        String sql = "INSERT INTO employers(user_id, company_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, null);
    }
    @Override
    public Optional<Employer> find(Long id) {
        String sql = "SELECT * FROM employers WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(Employer.class), id)));

    }

    @Override
    public void update(Employer obj) {
        String sql = "UPDATE employers SET company_name = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                obj.getCompanyName(),
                obj.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from employers where id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Optional<Employer> findByEmail(String email) {
        String sql = """
                SELECT * FROM employers WHERE user_id = ?
                """;

        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class), email)));
    }
}
