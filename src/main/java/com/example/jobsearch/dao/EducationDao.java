package com.example.jobsearch.dao;

import com.example.jobsearch.model.Education;
import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Education> getAllEducationByResumeId(long resumeId) {
        String sql = "select * from EDUCATION where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Education.class), resumeId);
    }
    public Education getEducationById(long id) {
        String sql = "select * from EDUCATION where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Education.class), id);
    }

    public void createEducation(Education e) {
        String sql = "insert into EDUCATION (RESUMEID, EDUCATION, SCHOOLNAME, STARTDATE, GRADUATIONDATE) \n" +
                "VALUES ( ?, ?, ?, ?, ? )";
        jdbcTemplate.update(sql, e.getResumeId(), e.getEducation(), e.getSchoolName(),
                e.getStartDate(), e.getGraduationDate());
    }
    public void deleteEducation(Education e) {
        String sql = "delete from EDUCATION where id = ?";
        jdbcTemplate.update(sql, e.getId());
    }
    public void editEducation(Education e) {
        String sql = """
                update EDUCATION
                set RESUMEID = ?, EDUCATION = ?, SCHOOLNAME = ?, STARTDATE = ?,\s
                    GRADUATIONDATE = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getResumeId(), e.getEducation(), e.getSchoolName(),
                e.getStartDate(), e.getGraduationDate(), e.getId());
    }
}
