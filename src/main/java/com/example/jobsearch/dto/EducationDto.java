package com.example.jobsearch.dto;

import com.example.jobsearch.validation.DatesComparison;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DatesComparison(startDate = "startDate", endDate = "graduationDate")
public class EducationDto {
    private Long id;
    private String education;
    private String schoolName;
    private LocalDate startDate;
    private LocalDate graduationDate;
}

