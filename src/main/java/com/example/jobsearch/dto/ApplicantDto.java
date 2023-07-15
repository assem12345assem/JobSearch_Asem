package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicantDto {
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
