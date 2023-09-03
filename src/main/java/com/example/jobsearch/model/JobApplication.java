package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobApplication {
    private Long id;
    private Long vacancyId;
    private Long resumeId;
    private LocalDateTime dateTime;
}

