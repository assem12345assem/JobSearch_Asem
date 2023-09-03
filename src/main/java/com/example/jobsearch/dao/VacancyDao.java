package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class VacancyDao extends BaseDao implements CrudDao<Vacancy, Long> {
    public VacancyDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies(employer_id, vacancy_name, category, salary, description, required_experience_min, required_experience_max, is_active, is_published, date_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, vacancy.getEmployerId());
            ps.setString(2, vacancy.getVacancyName());
            ps.setString(3, vacancy.getCategory());
            ps.setInt(4, vacancy.getSalary());
            ps.setString(5, vacancy.getDescription());
            ps.setInt(6, vacancy.getRequiredExperienceMin());
            ps.setInt(7, vacancy.getRequiredExperienceMax());
            ps.setBoolean(8, vacancy.isActive());
            ps.setBoolean(9, vacancy.isPublished());
            ps.setTimestamp(10, Timestamp.valueOf(vacancy.getDateTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Vacancy> find(Long id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(Vacancy.class), id)));

    }

    @Override
    public void update(Vacancy obj) {
        String sql = """
                UPDATE vacancies SET vacancy_name = ?, category = ?,
                salary = ?, description = ?,
                required_experience_min = ?, required_experience_max = ?,
                is_active = ?, is_published = ?,
                date_time = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,obj.getVacancyName(), obj.getCategory(), obj.getSalary(),
                obj.getDescription(), obj.getRequiredExperienceMin(),
                obj.getRequiredExperienceMax(), obj.isActive(),
                obj.isPublished(), obj.getDateTime(),
                obj.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from vacancies where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Vacancy> findByEmployerId(Long employerId) {
        String sql = "select * from VACANCIES where EMPLOYER_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), employerId);
    }

    public List<Vacancy> getAll() {
        String sql = "select * from VACANCIES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }
}
