package com.example.jobsearch.model;

import com.example.jobsearch.enums.UserType;
import lombok.*;

@Getter
@Setter
public class User {
    private int id;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String password;
}
