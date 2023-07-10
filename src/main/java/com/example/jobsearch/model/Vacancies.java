package com.example.jobsearch.model;

import com.example.jobsearch.enums.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

public class Vacancies {
    private int id;
    private EmployerResume vacancyPublisher;
    private Category category;
    private LocalDateTime dateTimeVacancyPublishment;
}
