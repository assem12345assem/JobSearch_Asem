package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Vacancy> getAllVacancies() {
        String sql = "select * from VACANCIES";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }
    public List<Vacancy> getAllVacanciesByEmployerId(long id) {
        String sql = "select * from VACANCIES where EMPLOYERID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
    }
    public List<Vacancy> getAllVacanciesByCategory(long categoryId) {
        String sql = "select * from VACANCIES where categoryId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
    }
    public Vacancy getVacancyById(Long id) {
        String sql = "select * from VACANCIES where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);

    }

    public List<Vacancy> getVacancyListByIdList(List<Long> id) {
        List<Vacancy> list = new ArrayList<>();
        id.forEach(e -> list.add(getVacancyById(e)));
        return list;
    }
    public void createVacancy(Vacancy vacancy) {
        String sql = """
        insert into VACANCIES (EMPLOYERID, VACANCYNAME, CATEGORYID, SALARY, DESCRIPTION,\s
                               REQUIREDEXPERIENCEMIN, REQUIREDEXPERIENCEMAX, ISACTIVE, ISPUBLISHED,\s
                               PUBLISHEDDATETIME)\s
        values ( ?,?,?,?,?,?,?,?,?,? )
        """;
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, vacancy.getEmployerId());
            ps.setString(2, vacancy.getVacancyName());
            ps.setLong(3, vacancy.getCategoryId());
            ps.setInt(4, vacancy.getSalary());
            ps.setString(5, vacancy.getDescription());
            ps.setInt(6, vacancy.getRequiredExperienceMin());
            ps.setInt(7, vacancy.getRequiredExperienceMax());
            ps.setBoolean(8, vacancy.isActive());
            ps.setBoolean(9, vacancy.isPublished());
            ps.setTimestamp(10, Timestamp.valueOf(vacancy.getPublishedDateTime()));
            return ps;    }
        );
    }

    public void editVacancy(Vacancy vacancy) {
        String sql = """
                update VACANCIES
                set VACANCYNAME = ?, CATEGORYID = ?, SALARY = ?, DESCRIPTION = ?, REQUIREDEXPERIENCEMIN = ?,
                    REQUIREDEXPERIENCEMAX = ?, ISACTIVE = ?, ISPUBLISHED = ?, PUBLISHEDDATETIME = ?
                where ID = ?
                """;
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, vacancy.getVacancyName());
            ps.setLong(2, vacancy.getCategoryId());
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

    public void deleteVacancy(Vacancy vacancy) {
        String sql = "delete from VACANCIES where id = ?";
        jdbcTemplate.update(sql, vacancy.getId());

    }
}

