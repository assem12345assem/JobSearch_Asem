package com.example.jobsearch.dao;

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
public class WorkExperienceDao extends BaseDao{
    public WorkExperienceDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        WorkExperience e = (WorkExperience) obj;
        String sql = "insert into WORKEXPERIENCE (RESUMEID, DATESTART, DATEEND, COMPANYNAME, POSITION, RESPONSIBILITIES) \n" +
                "values ( ?, ?, ?, ?, ?, ? )";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getResumeId());
            ps.setTimestamp(2, Timestamp.valueOf(e.getDateStart().atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(e.getDateEnd().atStartOfDay()));
            ps.setString(4, e.getCompanyName());
            ps.setString(5, e.getPosition());
            ps.setString(6, e.getResponsibilities());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from WORKEXPERIENCE where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public WorkExperience getWorkExperienceById(long id) {
        String sql = "select * from WORKEXPERIENCE where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WorkExperience.class), id);
    }
    public Optional<WorkExperience> findWorkExperienceById(long id) {
        String sql = "select * from WORKEXPERIENCE where id = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkExperience.class), id)));
    }
    public List<WorkExperience> getAllWorkExperienceByResumeId(long resumeId) {
        String sql = "select * from WORKEXPERIENCE where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkExperience.class), resumeId);
    }

    public void editWorkExperience(WorkExperience e) {
        String sql = """
                update WORKEXPERIENCE
                set RESUMEID = ?, DATESTART = ?, DATEEND = ?, COMPANYNAME = ?,\s
                    POSITION = ?, RESPONSIBILITIES = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getResumeId(), e.getDateStart(), e.getDateEnd(), e.getCompanyName(),
                e.getPosition(), e.getResponsibilities(), e.getId());
    }
}
