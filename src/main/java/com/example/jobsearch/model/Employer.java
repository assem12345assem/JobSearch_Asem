package com.example.jobsearch.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Employer {
    private Long id;
    private String userId;
    private String companyName;
}