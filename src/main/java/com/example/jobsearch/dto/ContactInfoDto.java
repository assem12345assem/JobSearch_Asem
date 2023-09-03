package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContactInfoDto {
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebook;
    private String linkedIn;
}

