package com.example.jobsearch.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class Resume {
    private long id;
    private long applicantId;
    private String resumeTitle;
    private long categoryId;
    private Integer expectedSalary;
    private boolean isActive;
    private boolean isPublished;
}
