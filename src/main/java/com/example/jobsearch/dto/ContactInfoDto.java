package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class ContactInfoDto {
    private long id;
    private long resumeId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebookAccount;
    private String linkedinAccount;
}
