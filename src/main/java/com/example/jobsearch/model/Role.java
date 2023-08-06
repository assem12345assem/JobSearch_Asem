package com.example.jobsearch.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {
    private Long id;
    private String role;
    private String user_email;
}
