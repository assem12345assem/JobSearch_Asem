package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobApplicationDto {
    private Long id;
    private Long vacancyId;
    private Long resumeId;
    private LocalDateTime dateTime;
}

