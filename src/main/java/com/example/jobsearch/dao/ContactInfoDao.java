package com.example.jobsearch.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;


@Component
public class ContactInfoDao extends BaseDao{
    public ContactInfoDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Object obj) {
        ContactInfo e = (ContactInfo) obj;
        String sql = """
                insert into CONTACTINFO (RESUMEID, TELEGRAM, EMAIL, PHONENUMBER, FACEBOOK, LINKEDIN)
                values ( ?, ?, ?, ?, ?, ? )
                """;
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, e.getResumeId());
            ps.setString(2, e.getTelegram());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getPhoneNumber());
            ps.setString(5, e.getFacebookAccount());
            ps.setString(6, e.getLinkedinAccount());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
    public Optional<ContactInfo> findContactInfoById(Long id) {
        String sql = "select * from contactinfo where id = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ContactInfo.class), id)));
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from CONTACTINFO where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ContactInfo getAllContactInfoByUserId(long resumeId) {
        String sql = "select * from CONTACTINFO where resumeId = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactInfo.class), resumeId);

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
