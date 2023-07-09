package com.example.jobsearch.model;

import com.example.jobsearch.enums.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {
    private int id;
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private UserType userType;
    @NonNull
    private String password;
}
