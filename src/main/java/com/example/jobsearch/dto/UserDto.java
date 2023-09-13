package com.example.jobsearch.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDto {
    @Email
    private String email;
    @Nullable
    @Pattern(regexp = "^[0-9 ()-]+$", message = "Phone number can only contain numbers, hyphens, spaces, and parentheses")
    private String phoneNumber;
    private String userName;
    private String userType;
    @NotBlank (message = "Please enter a password")
    private String password;
    private String photo;
    private String resetPasswordToken;

}
