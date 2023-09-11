package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContactInfoDto {
    private String telegram;
    @Email
    private String email;
    @Pattern(regexp = "^[0-9 ()-]+$", message = "Phone number can only contain numbers, hyphens, spaces, and parentheses")
    private String phoneNumber;
    private String facebook;
    private String linkedIn;
}

