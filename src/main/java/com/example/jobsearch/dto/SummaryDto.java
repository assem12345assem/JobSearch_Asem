package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SummaryDto {
    private Long id;
    private String title;
    private String isActive;
    private LocalDateTime dateTime;
}
