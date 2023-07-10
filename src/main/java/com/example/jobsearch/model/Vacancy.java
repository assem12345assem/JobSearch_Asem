package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vacancy {
    private int id;
    private int userId;
    private String vacancyName;
    private Integer salary;
    private String description;
    private String requiredJobExperience;
    private String category;
}
