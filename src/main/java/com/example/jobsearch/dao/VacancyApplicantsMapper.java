package com.example.jobsearch.dao;

import com.example.jobsearch.model.VacancyApplicants;

import org.springframework.jdbc.core.RowMapper;;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyApplicantsMapper implements RowMapper<VacancyApplicants> {
    @Override
    public VacancyApplicants mapRow(ResultSet rs, int rowNum) throws SQLException {
        VacancyApplicants va = new VacancyApplicants();
        va.setId(rs.getInt("id"));
        va.setApplicantUserId(rs.getInt("applicantUserId"));
        va.setResumeId(rs.getInt("resumeId"));
        va.setVacancyId(rs.getInt("vacancyId"));
        va.setDateTimeApplication(rs.getTimestamp("dateTimeApplication").toLocalDateTime());
        return va;
    }
}
