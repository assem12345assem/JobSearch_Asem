package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyDto {
    Long id;
    Long employerId;
    String vacancyName;
    String category;
    Integer salary;
    String description;
    Integer requiredExperienceMin;
    Integer requiredExperienceMax;
    boolean isActive;
    boolean isPublished;
    LocalDateTime publishedDateTime;
}
