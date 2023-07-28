package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull
    Long employerId;
    String vacancyName;
    String category;
    Integer salary;
    String description;
    @Positive(message = "Required minimum experience cannot be negative value")
    Integer requiredExperienceMin;
    @Positive(message = "Required maximum experience cannot be negative value")
    Integer requiredExperienceMax;
    boolean isActive;
    boolean isPublished;
    LocalDateTime publishedDateTime;
}
