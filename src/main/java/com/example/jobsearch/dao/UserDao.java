package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDao extends BaseDao implements CrudDao<User, String> {
    public UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Long save(User user) {
        return null;

    }

    public void saveUser(User user) {
        String sql = """
                INSERT INTO user_table
                (email, phone_number, user_name, user_type,
                password, photo, enabled)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.update(sql, user.getEmail(), user.getPhoneNumber(), user.getUserName(),
                user.getUserType(), user.getPassword(), user.getPhoto(), user.isEnabled());

    }


    @Override
    public Optional<User> find(String email) {
        String sql = """
                select * from user_table where email = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email)));

    }

    @Override
    public void update(User obj) {
        String sql = "UPDATE user_table set phone_number = ?, user_name = ? WHERE email = ?";

        jdbcTemplate.update(
                sql, obj.getPhoneNumber(), obj.getUserName(), obj.getEmail());
    }

    @Override
    public void delete(String id) {
        String sql = "delete from user_table where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void savePhoto(String email, String photo) {
        String sql = "update user_table set photo = ? where email = ?";
        jdbcTemplate.update(sql, photo, email);
    }
}
