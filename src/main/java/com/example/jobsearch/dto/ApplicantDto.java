package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
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
    @Email
    String userId;
    String firstName;
    String lastName;
    @Past
    LocalDate dateOfBirth;
}
