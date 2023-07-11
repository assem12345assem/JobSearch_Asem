package com.example.jobsearch.dao;

import com.example.jobsearch.enums.Category;
import com.example.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements RowMapper<Vacancy> {
    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vacancy v = new Vacancy();
        v.setId(rs.getInt("id"));
        v.setUserId(rs.getInt("userId"));
        v.setVacancyName(rs.getString("vacancyName"));
        v.setSalary(rs.getInt("salary"));
        v.setDescription(rs.getString("description"));
        v.setRequiredJobExperience(rs.getString("requiredJobExperience"));
        v.setCategory(Category.valueOf(rs.getString("category")));
        v.setDateTimeVacancyPublished(rs.getTimestamp("dateTimeVacancyPublished").toLocalDateTime());
       return v;
    }
}
