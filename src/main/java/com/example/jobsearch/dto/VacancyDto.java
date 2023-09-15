package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacancyDto {
    private Long id;
    private EmployerDto profile;
    private String vacancyName;
    private String category;
    private Integer salary;
    private String description;
    private Integer requiredExperienceMin;
    private Integer requiredExperienceMax;
    private String isActive;
    private boolean isPublished;
    private LocalDateTime dateTime;
}

