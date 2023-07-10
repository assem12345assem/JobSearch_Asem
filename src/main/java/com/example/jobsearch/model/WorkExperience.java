package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperience {
    private int id;
    private int resumeId;
    private int yearStart;
    private int yearEnd;
    private String companyName;
    private String position;
    private String responsibilities;
}
