package com.example.jobsearch.model;

import com.example.jobsearch.enums.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


public class EmployerResume {
    private int id;
    private int userId;
    private String vacancyName;
    private Integer salary;
    private String description;
    private String requiredJobExperience;
    private Category category;
}
