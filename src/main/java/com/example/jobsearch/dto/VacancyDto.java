package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VacancyDto {
    private long id;
    private EmployerDto employer;
    private String vacancyName;
    private CategoryDto category;
    private Integer salary;
    private String description;
    private int requiredExperienceMin;
    private int requiredExperienceMax;
    private boolean isActive;
    private boolean isPublished;
    private LocalDateTime publishedDateTime;
}
