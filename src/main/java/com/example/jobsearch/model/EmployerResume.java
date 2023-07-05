package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class EmployerResume {
    private int id;
    @NonNull
    private String vacancyName;
    @NonNull
    private Integer salary;
    @NonNull
    private String description;
    @NonNull
    private String requiredJobExperience;
    @NonNull
    private String category;
}
