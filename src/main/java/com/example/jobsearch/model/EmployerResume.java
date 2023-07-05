package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployerResume {
    private int id;
    private String vacancyName;
    private Integer salary;
    private String description;
    private String requiredJobExperience;
    private String category;
}
