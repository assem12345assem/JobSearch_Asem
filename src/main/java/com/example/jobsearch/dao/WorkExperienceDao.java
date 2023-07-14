package com.example.jobsearch.dao;

import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    public WorkExperience getWorkExperienceById(long id) {
        String sql = "select * from WORKEXPERIENCE where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WorkExperience.class), id);
    }
    public List<WorkExperience> getAllWorkExperienceByResumeId(long resumeId) {
        String sql = "select * from WORKEXPERIENCE where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkExperience.class), resumeId);
    }
    public void createWorkExperience(WorkExperience e) {
        String sql = "insert into WORKEXPERIENCE (RESUMEID, DATESTART, DATEEND, COMPANYNAME, POSITION, RESPONSIBILITIES) \n" +
                "values ( ?, ?, ?, ?, ?, ? )";
        jdbcTemplate.update(sql, e.getResumeId(), e.getDateStart(), e.getDateEnd(), e.getCompanyName(),
                e.getPosition(), e.getResponsibilities());
    }
    public void deleteWorkExperience(WorkExperience e) {
        String sql = "delete from WORKEXPERIENCE where id = ?";
        jdbcTemplate.update(sql, e.getId());
    }
    public void editWorkExperience(WorkExperience e) {
        String sql = "update WORKEXPERIENCE\n" +
                "set RESUMEID = ?, DATESTART = ?, DATEEND = ?, COMPANYNAME = ?, \n" +
                "    POSITION = ?, RESPONSIBILITIES = ?\n" +
                "where ID = ?";
        jdbcTemplate.update(sql, e.getResumeId(), e.getDateStart(), e.getDateEnd(), e.getCompanyName(),
                e.getPosition(), e.getResponsibilities(), e.getId());
    }
}
