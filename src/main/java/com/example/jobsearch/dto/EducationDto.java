package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationDto {
    private long id;
    private long resumeId;
    private String education;
    private String schoolName;
    private LocalDate startDate;
    private LocalDate graduationDate;
}
