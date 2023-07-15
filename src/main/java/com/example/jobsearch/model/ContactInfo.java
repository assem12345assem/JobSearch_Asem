package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class ContactInfo {
    private long id;
    private long resumeId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebookAccount;
    private String linkedinAccount;
}
