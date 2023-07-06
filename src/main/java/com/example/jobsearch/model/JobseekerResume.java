package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class JobseekerResume {
    private int id;
    @NonNull
    private String position;
    @NonNull
    private Integer expectedSalary;
    @NonNull
    private String workExperience;
    @NonNull
    private String education;
    @NonNull
    private ContactInfo contactInfo;

}
