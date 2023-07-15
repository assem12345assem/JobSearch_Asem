package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployerDto {
    private long id;
    private String userId;
    private String companyName;
}
