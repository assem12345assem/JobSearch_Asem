package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class VacancyApplicants {
    private JobseekerResume vacancyApplicant;
    private EmployerResume vacancy;
    private LocalDateTime dateTime;
}
