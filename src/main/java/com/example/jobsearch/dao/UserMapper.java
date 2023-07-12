package com.example.jobsearch.dao;

import com.example.jobsearch.enums.UserType;
import com.example.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setEmail(rs.getString("email"));
        u.setPhoneNumber(rs.getString("phoneNumber"));
        u.setUserType(UserType.valueOf(rs.getString("userType")));
        u.setPassword(rs.getString("password"));
        return u;
    }
}
