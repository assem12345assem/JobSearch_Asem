package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class JobApplicationDto {
    private long id;
    private VacancyDto vacancyDto;
    private ResumeDto resumeDto;
    private LocalDateTime dateTime;
}
