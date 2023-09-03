package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputResumeDto {
    String resumeTitle;
    String category;
    Integer expectedSalary;
    boolean isActive;
    boolean isPublished;
}
