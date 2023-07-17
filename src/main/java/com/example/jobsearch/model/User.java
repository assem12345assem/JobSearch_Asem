package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

//@Builder
@Getter
@Setter
public class User {
    private String id;
    private String phoneNumber;
    private String userType;
    private String password;
    private String photo;

}
