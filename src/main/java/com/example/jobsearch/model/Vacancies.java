package com.example.jobsearch.model;

import com.example.jobsearch.enums.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class Vacancies {
    private int id;
    @NonNull
    private EmployerResume vacancyPublisher;
    @NonNull
    private Category category;
    @NonNull
    private LocalDateTime dateTimeVacancyPublishment;
}
