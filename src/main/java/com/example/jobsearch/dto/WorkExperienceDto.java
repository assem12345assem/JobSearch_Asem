package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkExperienceDto {
    Long id;
    Long resumeId;
    LocalDate dateStart;
    LocalDate dateEnd;
    String companyName;
    String position;
    String responsibilities;
}
