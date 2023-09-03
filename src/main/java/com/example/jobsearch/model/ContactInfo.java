package com.example.jobsearch.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactInfo {
    private Long id;
    private Long resumeId;
    private String telegram;
    private String email;
    private String phoneNumber;
    private String facebook;
    private String linkedIn;
}

