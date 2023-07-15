package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
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
