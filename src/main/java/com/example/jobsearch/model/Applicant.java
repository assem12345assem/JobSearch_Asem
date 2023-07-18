package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class Applicant {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
