package com.example.jobsearch.model;

import com.example.jobsearch.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id;
    private String phoneNumber;
    private UserType userType;
    private String password;
}
