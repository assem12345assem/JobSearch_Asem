package com.example.jobsearch.dao;

import com.example.demo.entity.WorkExperience;
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

public class WorkExperienceDao extends BaseDao implements CrudDao<WorkExperience, Long> {
    public WorkExperienceDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(WorkExperience workExperience) {
        String sql = "INSERT INTO work_experience(resume_id, date_start, date_end, company_name, position, responsibilities) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, workExperience.getResumeId());
            if(workExperience.getDateStart() !=null) {
                ps.setDate(2, Date.valueOf(workExperience.getDateStart()));

            } else {
                ps.setDate(2, null);

            }
            if(workExperience.getDateEnd() !=null) {
                ps.setDate(3, Date.valueOf(workExperience.getDateEnd()));

            } else {
                ps.setDate(3, null);

            }
            ps.setString(4, workExperience.getCompanyName());
            ps.setString(5, workExperience.getPosition());
            ps.setString(6, workExperience.getResponsibilities());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<WorkExperience> find(Long id) {
        String sql = "SELECT * FROM work_experience WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(WorkExperience.class), id)));

    }

    @Override
    public void update(WorkExperience obj) {
        String sql = "UPDATE work_experience SET resume_id = ?, date_start = ?, date_end = ?, company_name = ?, position = ?, responsibilities = ? WHERE id = ?";

        jdbcTemplate.update(
                sql, obj.getResumeId(), obj.getDateStart(), obj.getDateEnd(), obj.getCompanyName(), obj.getPosition(), obj.getResponsibilities(), obj.getId());

    }

    @Override
    public void delete(Long id) {
String sql = "delete from WORK_EXPERIENCE where id = ?";
jdbcTemplate.update(sql, id);
    }

    public List<WorkExperience> findByResumeId(Long resumeId) {
        return jdbcTemplate.query("SELECT * FROM work_experience WHERE resume_id = ?",
                new BeanPropertyRowMapper<>(WorkExperience.class), resumeId);
    }
}
