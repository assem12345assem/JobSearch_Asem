package com.example.jobsearch.dto;

import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EducationDto {
    Long id;
    @NonNull
    Long resumeId;
    String education;
    String schoolName;
    @Past
    LocalDate startDate;
    LocalDate graduationDate;
}
