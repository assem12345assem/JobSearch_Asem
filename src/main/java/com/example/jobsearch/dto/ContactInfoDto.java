package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoDto {
    private long id;
    private long resumeId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebookAccount;
    private String linkedinAccount;
}
