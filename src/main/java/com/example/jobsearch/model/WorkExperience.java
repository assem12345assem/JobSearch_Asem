package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder

@Getter
@Setter
public class WorkExperience {
    private long id;
    private long resumeId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String companyName;
    private String position;
    private String responsibilities;
}
