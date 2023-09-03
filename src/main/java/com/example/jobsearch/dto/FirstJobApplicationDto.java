package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FirstJobApplicationDto {
    private Long id;
    private Long vacancyId;
    private Long resumeId;
    private String messageText;
    private LocalDateTime dateTime;
}

