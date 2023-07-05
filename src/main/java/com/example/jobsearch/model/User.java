package com.example.jobsearch.model;

import com.example.jobsearch.model.assist.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private int id;
    private String userName;
    private UserType userType;
    private String password;
}
