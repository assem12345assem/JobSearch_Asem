package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


public class JobseekerResume {
    private int id;
    private int userId;
    private String position;
    private Integer expectedSalary;
    private List<String> workExperience;
    private List<String> education;
    private ContactInfo contactInfo;

}
