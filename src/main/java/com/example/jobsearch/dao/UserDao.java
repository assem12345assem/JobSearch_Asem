package com.example.jobsearch.dao;

import com.example.jobsearch.common.UserMapper;
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
    public List<User> getUsersByUserType(String userType) {
        String sql = "select * from users where userType = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    public boolean checkUserPassword(String email, String password) {
        String sql = "select PASSWORD from USERS where ID = ?";
        User u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        //если введен неправильный email, возвращает null
        if(u == null) {
            return false;
        }
        return u.getPassword().equalsIgnoreCase(password);
        //если введен неправильный password, возвращает false
    }
    public boolean ifUserExists(String email) {
        String sql = "select * from users where id = ?";
        User u = jdbcTemplate.queryForObject(sql, new UserMapper(), email);
        return u != null;
    }
    public Optional<User> getOptionalUserById(String id) {
        String sql = "select * from users where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return Optional.ofNullable(user);
    }
    public Optional<User> getOptionalUserByPhoneNumber(String phoneNumber) {
        String sql = "select * from users where phoneNumber = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber);
        return Optional.ofNullable(user);
    }




}
