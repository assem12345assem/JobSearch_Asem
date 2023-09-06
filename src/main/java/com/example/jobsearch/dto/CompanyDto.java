package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDto {
    private String photo;
    private String name;
    private String phoneNumber;
    private String email;
}
