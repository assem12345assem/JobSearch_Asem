package com.example.jobsearch.model;

import lombok.*;

@Getter
@Setter
public class ContactInfo {
    private int id;
    private int userId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebookAccount;
    private String linkedinAccount;
}
