package com.example.jobsearch.model;

import com.example.jobsearch.model.assist.ContactInfo;
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
    private ContactInfo contactInfo;

}
