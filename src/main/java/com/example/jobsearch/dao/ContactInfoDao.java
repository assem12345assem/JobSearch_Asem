package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ContactInfoDao {
    private final JdbcTemplate jdbcTemplate;
    public ContactInfo getAllContactInfoByUserId(long resumeId) {
        String sql = "select * from CONTACTINFO where resumeId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactInfo.class), resumeId);

    }

    public void createContactInfo(ContactInfo e) {
        String sql = "insert into CONTACTINFO (RESUMEID, TELEGRAM, EMAIL, PHONENUMBER, FACEBOOK, LINKEDIN)\n" +
                "values ( ?, ?, ?, ?, ?, ? )\n";
        jdbcTemplate.update(sql, e.getResumeId(), e.getTelegram(), e.getEmail(), e.getPhoneNumber(),
                e.getFacebookAccount(), e.getLinkedinAccount());
    }
    public void deleteContactInfo(ContactInfo e) {
        String sql = "delete from CONTACTINFO where id = ?";
        jdbcTemplate.update(sql, e.getId());
    }
    public void editContactInfo(ContactInfo e) {
        String sql = """
                update CONTACTINFO
                set RESUMEID = ?, TELEGRAM = ?, EMAIL = ?, PHONENUMBER = ?,\s
                    FACEBOOK = ?, LINKEDIN = ?
                where ID = ?""";
        jdbcTemplate.update(sql, e.getResumeId(), e.getTelegram(), e.getEmail(), e.getPhoneNumber(),
                e.getFacebookAccount(), e.getLinkedinAccount(), e.getId());
    }
}
