package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class Applicant {
    private long id;
    private long userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
