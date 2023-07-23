package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobApplicationDto {
    Long id;
    VacancyDto vacancyDto;
    ResumeDto resumeDto;
    LocalDateTime dateTime;
}
