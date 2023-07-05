package com.example.jobsearch.model;

import com.example.jobsearch.model.assist.ContactInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobseekerResume {
    private int id;
    private String position;
    private Integer expectedSalary;
    private ContactInfo contactInfo;

}
