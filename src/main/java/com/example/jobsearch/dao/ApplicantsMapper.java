package com.example.jobsearch.dao;

import com.example.jobsearch.model.Applicants;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantsMapper implements RowMapper<Applicants> {
    @Override
    public Applicants mapRow(ResultSet rs, int rowNum) throws SQLException {
        Applicants va = new Applicants();
        va.setId(rs.getInt("id"));
        va.setApplicantUserId(rs.getInt("applicantUserId"));
        va.setResumeId(rs.getInt("resumeId"));
        va.setVacancyId(rs.getInt("vacancyId"));
        va.setDateTimeApplication(rs.getTimestamp("dateTimeApplication").toLocalDateTime());
        return va;
    }
}
