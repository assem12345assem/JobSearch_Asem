package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class VacancyDao extends BaseDao{
    public VacancyDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
    @Override
    public Long save(Object obj) {
        Vacancy e = (Vacancy) obj;
        String sql = """
                insert into VACANCIES (EMPLOYER_ID, VACANCY_NAME, CATEGORY, SALARY, DESCRIPTION,\s
                               REQUIRED_EXPERIENCE_MIN, REQUIRED_EXPERIENCE_MAX, IS_ACTIVE, IS_PUBLISHED,\s
                               PUBLISHED_DATE_TIME)
                        values ( ?,?,?,?,?,?,?,?,?,? )""";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getEmployerId());
            ps.setString(2, e.getVacancyName());
            ps.setString(3, e.getCategory());
            ps.setInt(4, e.getSalary());
            ps.setString(5, e.getDescription());
            ps.setInt(6, e.getRequiredExperienceMin());
            ps.setInt(7, e.getRequiredExperienceMax());
            ps.setBoolean(8, e.isActive());
            ps.setBoolean(9, e.isPublished());
            ps.setTimestamp(10, Timestamp.valueOf(e.getPublishedDateTime()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
    @Override
    public void delete(Long id) {
        String sql = "delete from VACANCIES where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Vacancy> getAllVacancies() {
        String sql = "select * from VACANCY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }
    public List<Vacancy> getAllVacanciesByEmployerId(long id) {
        String sql = "select * from VACANCY where EMPLOYER_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
    }
    public List<Vacancy> getAllVacanciesByCategory(String category) {
        String sql = "select * from VACANCY where category = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), category);
    }
    public Vacancy getVacancyById(Long id) {
        String sql = "select * from VACANCY where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);

    }
    public Optional<Vacancy> findVacancyById(Long id) {
        String sql = "select * from VACANCY where id = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id)));
    }

    public List<Vacancy> getVacancyListByIdList(List<Long> id) {
        List<Vacancy> list = new ArrayList<>();
        id.forEach(e -> list.add(getVacancyById(e)));
        return list;
    }

    public void editVacancy(Vacancy vacancy) {
        String sql = """
                update VACANCIES
                set VACANCY_NAME = ?, CATEGORY = ?, SALARY = ?, DESCRIPTION = ?, REQUIRED_EXPERIENCE_MIN = ?,
                    REQUIRED_EXPERIENCE_MAX = ?, IS_ACTIVE = ?, IS_PUBLISHED = ?, PUBLISHED_DATE_TIME = ?
                where ID = ?
                """;
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, vacancy.getVacancyName());
            ps.setString(2, vacancy.getCategory());
            ps.setInt(3, vacancy.getSalary());
            ps.setString(4, vacancy.getDescription());
            ps.setInt(5, vacancy.getRequiredExperienceMin());
            ps.setInt(6, vacancy.getRequiredExperienceMax());
            ps.setBoolean(7, vacancy.isActive());
            ps.setBoolean(8, vacancy.isPublished());
            ps.setTimestamp(9, Timestamp.valueOf(vacancy.getPublishedDateTime()));
            ps.setLong(10, vacancy.getId());
            return ps;    }
        );
    }



}

