package com.example.jobsearch.dto;

import com.example.jobsearch.validation.DatesComparison;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DatesComparison(startDate = "dateStart", endDate = "dateEnd")
public class WorkExperienceDto {
    private Long id;
    private String companyName;
    private String position;
    private String responsibilities;
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
