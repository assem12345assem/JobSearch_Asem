package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    public User getUserById(int id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }
    public Optional<User> getOptionalUserById(int id) {
        String sql = "select * from users where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return Optional.ofNullable(user);
    }




}
