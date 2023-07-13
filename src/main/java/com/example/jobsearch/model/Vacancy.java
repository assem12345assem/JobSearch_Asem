package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vacancy {
    private long id;
    private long employerId;
    private String vacancyName;
    private Category category;
    private Integer salary;
    private String description;
    private int requiredExperienceMin;
    private int requiredExperienceMax;
    private boolean isActive;
    private boolean isPublished;
    private LocalDateTime publishedDateTime;
}
