package com.example.jobsearch.model.assist;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
@Data
@Builder

public class ContactInfo {
    @NonNull
    private String telegram;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    private String facebookAccount;
    private Linkedin linkedin;
}
