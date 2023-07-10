package com.example.jobsearch.model;

import com.example.jobsearch.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vacancy {
    private int id;
    private int userId;
    private String vacancyName;
    private Integer salary;
    private String description;
    private String requiredJobExperience;
    private Category category;
    private LocalDateTime dateTimeVacancyPublished;
}
