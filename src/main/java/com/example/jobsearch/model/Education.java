package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Education {
    private int id;
    private int resumeId;
    private String education;
    private String schoolName;
    private int graduationYear;
}
