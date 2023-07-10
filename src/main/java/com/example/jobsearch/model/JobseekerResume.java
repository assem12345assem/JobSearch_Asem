package com.example.jobsearch.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class JobseekerResume {
    private int id;
    private int userId;
    private String position;
    private Integer expectedSalary;
    private List<String> workExperience;
    private List<String> education;
    private ContactInfo contactInfo;

}
