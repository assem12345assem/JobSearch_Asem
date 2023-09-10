package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDto {
    @Email
    private String email;
    private String phoneNumber;
    private String userName;
    @NotBlank (message = "Please select your user type")
    private String userType;
    @NotBlank (message = "Please enter a password")
    private String password;
    private String photo;
}
