package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    @Email
    private String email;
    private String phoneNumber;
    private String userName;
    private String userType;
    @NotBlank
    private String password;
    private String photo;
}
