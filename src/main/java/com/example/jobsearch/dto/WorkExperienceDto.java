package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceDto {
    private long id;
    private long resumeId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String companyName;
    private String position;
    private String responsibilities;
}
