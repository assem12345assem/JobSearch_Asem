package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Builder
public class Applicant {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
