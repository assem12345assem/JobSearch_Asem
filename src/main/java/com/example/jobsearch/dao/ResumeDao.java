package com.example.jobsearch.dao;

import com.example.demo.entity.Resume;
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

public class ResumeDao extends BaseDao implements CrudDao<Resume, Long> {
    public ResumeDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Resume resume) {
        String sql = "INSERT INTO resumes(applicant_id, resume_title, category, expected_salary, is_active, is_published, date_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, resume.getApplicantId());
            ps.setString(2, resume.getResumeTitle());
            ps.setString(3, resume.getCategory());
            ps.setInt(4, resume.getExpectedSalary());
            ps.setBoolean(5, resume.isActive());
            ps.setBoolean(6, resume.isPublished());
            ps.setTimestamp(7, Timestamp.valueOf(resume.getDateTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Resume> find(Long id) {
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT * FROM resumes WHERE id = ?",
                        new BeanPropertyRowMapper<>(Resume.class), id))) ;   }

    @Override
    public void update(Resume obj) {
        String sql = "UPDATE resumes SET applicant_id = ?, resume_title = ?, category = ?, expected_salary = ?, is_active = ?, is_published = ?, date_time = ? WHERE id = ?";

        jdbcTemplate.update(
                sql, obj.getApplicantId(), obj.getResumeTitle(), obj.getCategory(), obj.getExpectedSalary(), obj.isActive(), obj.isPublished(), obj.getDateTime(), obj.getId());

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from resumes where id = ?";
        jdbcTemplate.update(sql, id);
    }
    public List<Resume> findByApplicantId(Long applicantId){
           return
                jdbcTemplate.query("SELECT * FROM resumes WHERE applicant_id = ?",
                        new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }

    public List<Resume> getAll() {
        String sql = "SELECT * FROM resumes";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Resume.class));

}
}
