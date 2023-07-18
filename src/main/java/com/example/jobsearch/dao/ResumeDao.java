package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Resume> getAllResumes() {
        System.out.println("DDDDDDDDDDAAAAAAAAAAAOOOOOOOOOOOOOOOO");
        String sql = "select * from RESUMES";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
        List<Resume> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
        System.out.println(list.size());
        return list;
    }
    public Resume getResumeById(long id) {
        String sql = "select * from RESUMES where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Resume.class), id);
    }
    public List<Resume> getAllResumesByApplicantId(long applicantId) {
        String sql = "select * from RESUMES where applicantId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }
    public List<Resume> getAllResumesByCategoryId(long categoryId) {
        String sql = "select * from RESUMES where categoryId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }
    public List<Resume> getAllResumesByLongList(List<Long> list) {
        List<Resume> temp = new ArrayList<>();
        for (Long l:
             list) {
            temp.add(getResumeById(l));
        }
        return temp;
    }

    public List<Resume> getAllResumesByCategoryName(String categoryName) {
        String sql = """
                select * from RESUMES r, CATEGORIES c
                         where r.CATEGORYID = c.id
                           and c.CATEGORY = ?""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryName);
    }
    public void createResume(Resume e) {
        String sql = """
                insert into RESUMES (APPLICANTID, RESUMETITLE, CATEGORYID,\s
                                     EXPECTEDSALARY, ISACTIVE, ISPUBLISHED)\s
                VALUES ( ?, ?, ?, ?, ?, ? )""";
        jdbcTemplate.update(sql, e.getApplicantId(), e.getResumeTitle(), e.getCategoryId(),
                e.getExpectedSalary(), e.isActive(), e.isPublished());
    }
    public void deleteResume(Resume e) {
        String sql = "delete from RESUMES where id = ?";
        jdbcTemplate.update(sql, e.getId());
    }
    public void editResume(Resume e) {
        String sql = """
                update RESUMES
                set APPLICANTID = ?, RESUMETITLE = ?, CATEGORYID = ?, EXPECTEDSALARY = ?,\s
                    ISACTIVE = ?, ISPUBLISHED = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getApplicantId(), e.getResumeTitle(), e.getCategoryId(),
                e.getExpectedSalary(), e.isActive(), e.isPublished(), e.getId());
    }


    public List<Resume> getResumeByResumeTitle(String title) {
        String sql = "select * from RESUMES where RESUMETITLE like ?";
        title = "%" + title + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), title);
    }
}
