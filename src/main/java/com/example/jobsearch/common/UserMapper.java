package com.example.jobsearch.common;

import com.example.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
        u.setId(rs.getString("id"));
        u.setPhoneNumber(rs.getString("phoneNumber"));
        u.setUserType(rs.getString("userType"));
        u.setPassword(rs.getString("password"));
        return u;
    }


}
