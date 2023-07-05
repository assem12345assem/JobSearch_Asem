package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
@Builder
public class VacancyApplicants {
    private int id;
    @NonNull
    private JobseekerResume vacancyApplicant;
    @NonNull
    private EmployerResume vacancy;
    @NonNull
    private LocalDateTime dateTimeApplication;
}
