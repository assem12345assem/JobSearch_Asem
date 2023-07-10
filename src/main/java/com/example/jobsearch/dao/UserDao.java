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
    public List<User> getAllJobseekers() {
        String sql = "select * from users where userType = 'JOBSEEKER'";
        return jdbcTemplate.query(sql, new UserMapper());
    }
    public List<User> getAllEmployers() {
        String sql = "select * from users where userType = 'EMPLOYER'";
        return jdbcTemplate.query(sql, new UserMapper());
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
    public Optional<User> getOptionalUserByFirstName(String firstName) {
        String sql = "select * from users where firstName = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), firstName);
        return Optional.ofNullable(user);
    }
    public Optional<User> getOptionalUserByLastName(String lastName) {
        String sql = "select * from users where lastName = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), lastName);
        return Optional.ofNullable(user);
    }
    public Optional<User> getOptionalUserByEmail(String email) {
        String sql = "select * from users where email = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        return Optional.ofNullable(user);
    }
    public Optional<User> getOptionalUserByPhoneNumber(String phoneNumber) {
        String sql = "select * from users where phoneNumber = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber);
        return Optional.ofNullable(user);
    }




}
