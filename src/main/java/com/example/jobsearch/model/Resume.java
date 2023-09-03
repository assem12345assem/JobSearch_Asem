package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Resume {
    private Long id;
    private Long applicantId;
    private String resumeTitle;
    private String category;
    private Integer expectedSalary;
    private boolean isActive;
    private boolean isPublished;
    private LocalDateTime dateTime;
}
