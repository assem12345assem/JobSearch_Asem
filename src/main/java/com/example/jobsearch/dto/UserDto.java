package com.example.jobsearch.dto;

import com.example.jobsearch.enums.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String phoneNumber;
    private UserType userType;
    private String password;
}
