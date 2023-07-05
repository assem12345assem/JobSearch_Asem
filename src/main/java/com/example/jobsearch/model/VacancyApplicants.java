package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class VacancyApplicants {
    @NonNull
    private JobseekerResume vacancyApplicant;
    @NonNull
    private EmployerResume vacancy;
    @NonNull
    private LocalDateTime dateTime;
}
