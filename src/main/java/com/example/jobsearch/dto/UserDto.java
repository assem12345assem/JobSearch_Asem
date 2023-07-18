package com.example.jobsearch.dto;

import com.example.jobsearch.enums.UserType;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String phoneNumber;
    private UserType userType;
    private String password;
    private String photo;
}
