package com.example.jobsearch.dao;

import com.example.jobsearch.model.JobApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JobApplicationDao extends BaseDao {
    public JobApplicationDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        JobApplication e = (JobApplication) obj;
        String sql = "insert into JOBAPPLICATIONS (VACANCYID, RESUMEID, DATETIME)\n" +
                "VALUES ( ?,?,? )";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getVacancyId());
            ps.setLong(2, e.getResumeId());
            ps.setTimestamp(3, Timestamp.valueOf(e.getDateTime()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from jobapplications where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<JobApplication> getAllJobApplications() {
        String sql = "select * from JOBAPPLICATIONS";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobApplication.class));
    }

    public List<Long> getAllResumeIdsByVacancyId(long vacancyId) {
        String sql = "select RESUMEID from JOBAPPLICATIONS where VACANCYID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class), vacancyId);
    }

    public List<Long> getAllVacanciesByResumeId(long resumeId) {
        String sql = "select VACANCYID from JOBAPPLICATIONS where RESUMEID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class), resumeId);
    }

    public List<Long> getAllVacanciesByResumeList(List<Long> resumeList) {
        List<Long> myVacancyIds = new ArrayList<>();
        for (Long l :
                resumeList) {
            List<Long> tempList;
            tempList = getAllVacanciesByResumeId(l);
            myVacancyIds.addAll(tempList);
        }
        return myVacancyIds;
    }

    public JobApplication getVacancyByIdAndResumeId(long vacancyId, long resumeId) {
        String sql = "select * from jobapplications where vacancyid = ? and resumeid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(JobApplication.class), vacancyId, resumeId);
    }
}
