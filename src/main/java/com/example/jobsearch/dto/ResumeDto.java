package com.example.jobsearch.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeDto {
    Long id;
    ApplicantDto applicantDto;
    String resumeTitle;
    CategoryDto categoryDto;
    Integer expectedSalary;
    boolean isActive;
    boolean isPublished;
}
