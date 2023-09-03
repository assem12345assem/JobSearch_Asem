package com.example.jobsearch.dao;

import com.example.jobsearch.model.JobApplication;
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
public class JobApplicationDao extends BaseDao implements CrudDao<JobApplication, Long> {
    public JobApplicationDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(JobApplication obj) {
        String sql = "insert into JOB_APPLICATIONS(vacancy_id, resume_id, date_time) VALUES ( ?, ?, ? )";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, obj.getVacancyId());
            ps.setLong(2, obj.getResumeId());
            ps.setTimestamp(3, Timestamp.valueOf(obj.getDateTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<JobApplication> find(Long id) {
        String sql = "select * from JOB_APPLICATIONS where ID = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(JobApplication.class), id)));


    }

    @Override
    public void update(JobApplication obj) {
//update method not needed for job applications.
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from job_applications where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<JobApplication> findByResumeId(Long id) {
        String sql = "select * from JOB_APPLICATIONS where resume_id = ?";
        return
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(JobApplication.class), id);

    }

    public List<JobApplication> findByVacancyId(Long id) {
        String sql = "select * from JOB_APPLICATIONS where vacancy_id = ?";
        return
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(JobApplication.class), id);

    }
}
