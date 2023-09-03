package com.example.jobsearch.dao;

import com.example.jobsearch.model.Message;
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
public class MessageDao extends BaseDao implements CrudDao<Message, Long> {
    public MessageDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(Message obj) {
        String sql = "insert into MESSAGES(job_application_id, message, author, create_time) VALUES ( ?, ?, ?, ? )";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, obj.getJobApplicationId());
            ps.setString(2, obj.getMessage());
            ps.setString(3, obj.getAuthor());
            ps.setTimestamp(4, Timestamp.valueOf(obj.getCreateTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();

    }

    @Override
    public Optional<Message> find(Long id) {
        String sql = "select * from messages where ID = ?";
        return Optional.of(DataAccessUtils.requiredSingleResult(
                jdbcTemplate.query(sql,
                        new BeanPropertyRowMapper<>(Message.class), id)));


    }

    @Override
    public void update(Message obj) {
//method not necessary for messageDao

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from messages where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Message> getAllByJobApplication(Long jobApplicationId) {
        String sql = "select * from MESSAGES where JOB_APPLICATION_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Message.class), jobApplicationId);
    }
}
