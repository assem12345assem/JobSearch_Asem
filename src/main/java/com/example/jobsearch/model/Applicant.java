package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Applicant {
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
