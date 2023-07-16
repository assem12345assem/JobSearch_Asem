package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class Employer {
    private long id;
    private String userId;
    private String companyName;
}
