package com.example.jobsearch.model;

import com.example.jobsearch.enums.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {
    private int id;
    @NonNull
    private String userName;
    @NonNull
    private UserType userType;
    @NonNull
    private String password;
}
