package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Education {

    private Long id;
    private Long resumeId;
    private String education;
    private String schoolName;
    private LocalDate startDate;
    private LocalDate graduationDate;
}

