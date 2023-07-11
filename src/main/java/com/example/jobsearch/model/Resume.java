package com.example.jobsearch.model;

import com.example.jobsearch.enums.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Resume {
    private int id;
    private int userId;
    private String position;
    private Integer expectedSalary;
    private List<WorkExperience> workExperience;
    private List<Education> education;
    private ContactInfo contactInfo;
    private Category category;

}
