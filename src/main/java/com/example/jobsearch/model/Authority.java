package com.example.jobsearch.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authority {
    private Long id;
    private String authority;
    private String email;
}

