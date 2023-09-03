package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployerDto {
    private Long id;
    private String companyName;
}