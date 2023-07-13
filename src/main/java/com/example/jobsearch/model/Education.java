package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Education {
    private long id;
    private long resumeId;
    private String education;
    private String schoolName;
    private LocalDate startDate;
    private LocalDate graduationDate;
}
