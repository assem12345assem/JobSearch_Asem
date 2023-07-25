package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicantDto {
    Long id;
    String userId;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
