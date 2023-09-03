package com.example.jobsearch.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private String email;
    private String phoneNumber;
    private String userName;
    private String userType;
    private String password;
    private String photo;
    private boolean enabled;
}
