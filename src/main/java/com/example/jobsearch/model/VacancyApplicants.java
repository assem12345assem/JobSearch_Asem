package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

public class VacancyApplicants {
    private int id;
    private JobseekerResume vacancyApplicant;
    private EmployerResume vacancy;
    private LocalDateTime dateTimeApplication;
}
