package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EditProfileDto {
    private String email;
    private String phoneNumber;
    private String userName;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String companyName;
}
