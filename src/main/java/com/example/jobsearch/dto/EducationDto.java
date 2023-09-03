package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EducationDto {
    private Long id;
    private String education;
    private String schoolName;
    private LocalDate startDate;
    private LocalDate graduationDate;
}

