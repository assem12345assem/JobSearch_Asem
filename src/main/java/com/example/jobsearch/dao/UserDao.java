package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao extends BaseDao{
    public UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }



    @Override
    public Long save(Object obj) {
        User u = (User) obj;
        return (long) jdbcTemplate.update(
                "insert into users(id, password, enabled) values (?, ?, ?)",
                u.getId(),
                u.getPassword(),
                u.isEnabled()
        );
    }

    public void delete(String id) {
        String sql = "delete from users where id = ?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public void delete(Long id) {

    }

    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    public List<User> getUsersByUserType(String userType) {
        String sql = "select * from users where USER_TYPE = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    public boolean ifPasswordCorrect(String email, String password) {
        String sql = "select PASSWORD from USERS where ID = ?";
        User u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        if(u == null) {
            return false;
        }
        return u.getPassword().equalsIgnoreCase(password);
    }
    private String setNull(String string) {
        if(string.length() == 0) {
            return null;
        }
        return string;
    }
    public boolean ifUserExists(String email) {
        String sql = "select * from users where id = ?";

        List<User> user = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
        return !user.isEmpty();
    }
    public Optional<User> getOptionalUserById(String id) {
        String sql = "select * from users where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return Optional.ofNullable(user);
    }
    public Optional<User> getOptionalUserByPhoneNumber(String phoneNumber) {
        String sql = "select * from users where PHONE_NUMBER = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber);
        return Optional.ofNullable(user);
    }


    public void editUser(User e) {
        String sql = """
                update USERS set PHONE_NUMBER = ?, USER_NAME = ?, USER_TYPE = ?,
                                             PASSWORD = ?, PHOTO = ?, ENABLED = ?
                            where id = ?""";
        jdbcTemplate.update(sql, e.getPhoneNumber(), e.getUserName(), e.getUserType(),
                e.getPassword(), e.getPhoto(), e.isEnabled(), e.getId());
    }

    public void savePhoto(String email, String photo) {
        String sql = "update users set photo = ? where id = ?";
        jdbcTemplate.update(sql, photo, email);
    }

    public User getUserById(String email) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }
}
