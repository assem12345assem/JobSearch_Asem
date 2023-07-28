package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ResumeDao extends BaseDao{
    public ResumeDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        Resume e = (Resume) obj;
        String sql = """
                insert into RESUMES (APPLICANTID, RESUMETITLE, CATEGORY,\s
                                     EXPECTEDSALARY, ISACTIVE, ISPUBLISHED)\s
                VALUES ( ?, ?, ?, ?, ?, ? )""";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getApplicantId());
            ps.setString(2, e.getResumeTitle());
            ps.setString(3, e.getCategory());
            ps.setInt(4, e.getExpectedSalary());
            ps.setBoolean(5, e.isActive());
            ps.setBoolean(6, e.isPublished());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from RESUMES where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Resume> getAllResumes() {
        String sql = "select * from RESUMES";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
        List<Resume> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
        System.out.println(list.size());
        return list;
    }
    public Optional<Resume> getResumeById(long id) {
        String sql = "select * from RESUMES where id = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)));
    }
    public Resume findResumeById(long id) {
        String sql = "select * from RESUMES where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Resume.class), id);
    }
    public List<Resume> getAllResumesByApplicantId(long applicantId) {
        String sql = "select * from RESUMES where applicantId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }
    public List<Resume> getAllResumesByCategory(String categoryId) {
        String sql = "select * from RESUMES where category = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }
    public List<Resume> getAllResumesByLongList(List<Long> list) {
        List<Resume> temp = new ArrayList<>();
        for (Long l:
             list) {
            temp.add(findResumeById(l));
        }
        return temp;
    }

    public List<Resume> getAllResumesByCategoryName(String categoryName) {
        String sql = """
                select * from RESUMES r, CATEGORIES c
                         where r.CATEGORY = c.CATEGORY
                           and c.CATEGORY = ?""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryName);
    }
    public void editResume(Resume e) {
        String sql = """
                update RESUMES
                set APPLICANTID = ?, RESUMETITLE = ?, CATEGORY = ?, EXPECTEDSALARY = ?,\s
                    ISACTIVE = ?, ISPUBLISHED = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getApplicantId(), e.getResumeTitle(), e.getCategory(),
                e.getExpectedSalary(), e.isActive(), e.isPublished(), e.getId());
    }


    public List<Resume> getResumeByResumeTitle(String title) {
        String sql = "select * from RESUMES where RESUMETITLE like ?";
        title = "%" + title + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), title);
    }
}
