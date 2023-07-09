package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public class ContactInfo {
    private int id;
    private int userId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebookAccount;
    private String linkedinAccount;
}
