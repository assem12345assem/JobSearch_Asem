package com.example.jobsearch.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vacancy {
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
