package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Vacancy {
    private Long id;
    private Long employerId;
    private String vacancyName;
    private String category;
    private Integer salary;
    private String description;
    private Integer requiredExperienceMin;
    private Integer requiredExperienceMax;
    private boolean isActive;
    private boolean isPublished;
    private LocalDateTime dateTime;
}

