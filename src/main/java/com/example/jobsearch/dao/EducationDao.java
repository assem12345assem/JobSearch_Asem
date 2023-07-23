package com.example.jobsearch.dao;

import com.example.jobsearch.model.Education;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Component
public class EducationDao extends BaseDao{
    public EducationDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        Education e = (Education) obj;
        String sql = """
                insert into EDUCATION (RESUMEID, EDUCATION, SCHOOLNAME, STARTDATE, GRADUATIONDATE)
                VALUES ( ?, ?, ?, ?, ? )
                """;
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getResumeId());
            ps.setString(2, e.getEducation());
            ps.setString(3, e.getSchoolName());
            ps.setTimestamp(4, Timestamp.valueOf(e.getStartDate().atStartOfDay()));
            ps.setTimestamp(5, Timestamp.valueOf(e.getGraduationDate().atStartOfDay()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from EDUCATION where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Education> getAllEducationByResumeId(long resumeId) {
        String sql = "select * from EDUCATION where resumeId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Education.class), resumeId);
    }
    public Education getEducationById(long id) {
        String sql = "select * from EDUCATION where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Education.class), id);
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
