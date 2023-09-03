package com.example.jobsearch.dao;

import com.example.demo.entity.Education;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component

public class EducationDao extends BaseDao implements CrudDao<Education, Long> {
    public EducationDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Education education) {
        String sql = "INSERT INTO education(resume_id, education, school_name, start_date, graduation_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, education.getResumeId());
            ps.setString(2, education.getEducation());
            ps.setString(3, education.getSchoolName());
            if(education.getStartDate() !=null) {
                ps.setDate(4, Date.valueOf(education.getStartDate()));
            } else {
                ps.setDate(4, null);
            }
            if(education.getGraduationDate() !=null) {
                ps.setDate(5, Date.valueOf(education.getGraduationDate()));

            } else {
                ps.setDate(5, null);
            }return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Education> find(Long id) {
        String sql = "SELECT * FROM education WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(Education.class), id)));
    }

    @Override
    public void update(Education obj) {
        String sql = "UPDATE education SET resume_id = ?, education = ?, school_name = ?, start_date = ?, graduation_date = ? WHERE id = ?";

        jdbcTemplate.update(
                sql, obj.getResumeId(), obj.getEducation(), obj.getSchoolName(), obj.getStartDate(), obj.getGraduationDate(), obj.getId());

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from education where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Education> findByResumeId(Long resumeId) {
        return jdbcTemplate.query("SELECT * FROM education WHERE resume_id = ?",
                        new BeanPropertyRowMapper<>(Education.class), resumeId);
    }
}
