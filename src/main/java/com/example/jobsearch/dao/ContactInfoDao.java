package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactInfo;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Component

public class ContactInfoDao extends BaseDao implements CrudDao<ContactInfo, Long> {
    public ContactInfoDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(ContactInfo contactInfo) {
        String sql = "INSERT INTO contact_info(resume_id, telegram, email, phone_number, facebook, linkedIn) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, contactInfo.getResumeId());
            ps.setString(2, contactInfo.getTelegram());
            ps.setString(3, contactInfo.getEmail());
            ps.setString(4, contactInfo.getPhoneNumber());
            ps.setString(5, contactInfo.getFacebook());
            ps.setString(6, contactInfo.getLinkedIn());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<ContactInfo> find(Long id) {
        String sql = "SELECT * FROM contact_info WHERE id = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(ContactInfo.class), id)));


    }

    @Override
    public void update(ContactInfo obj) {
        String sql = "UPDATE contact_info SET resume_id = ?, telegram = ?, email = ?, phone_number = ?, facebook = ?, linkedin = ? WHERE id = ?";

        jdbcTemplate.update(
                sql, obj.getResumeId(), obj.getTelegram(), obj.getEmail(), obj.getPhoneNumber(), obj.getFacebook(), obj.getLinkedIn(), obj.getId());

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from contact_info where id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Optional<ContactInfo> findByResumeId(Long resumeId) {
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT * FROM contact_info WHERE resume_id = ?",
                        new BeanPropertyRowMapper<>(ContactInfo.class), resumeId)
        ));
    }
}
