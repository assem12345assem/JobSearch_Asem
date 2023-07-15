package com.example.jobsearch.dao;

import com.example.jobsearch.model.JobApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobApplicationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<JobApplication> getAllJobApplications() {
        String sql ="select * from JOBAPPLICATIONS";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobApplication.class));
    }
    public List<Long> getAllResumeIdsByVacancyId(long vacancyId) {
        String sql ="select RESUMEID from JOBAPPLICATIONS where VACANCYID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class), vacancyId);
    }
    public List<Long> getAllVacanciesByResumeId(long resumeId) {
        String sql ="select VACANCYID from JOBAPPLICATIONS where RESUMEID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Long.class), resumeId);
    }
    public List<Long> getAllVacanciesByResumeList(List<Long> resumeList) {
        List<Long> myVacancyIds = new ArrayList<>();
        for (Long l:
             resumeList) {
            List<Long> tempList;
            tempList = getAllVacanciesByResumeId(l);
            myVacancyIds.addAll(tempList);
        }
        return myVacancyIds;
    }



}
