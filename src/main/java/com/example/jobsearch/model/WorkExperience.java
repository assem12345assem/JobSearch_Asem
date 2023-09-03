package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkExperience {
    private Long id;
    private Long resumeId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String companyName;
    private String position;
    private String responsibilities;
}
