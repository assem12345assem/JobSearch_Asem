package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfoDto {
    Long id;
    Long resumeId;
    String telegram;
    String email;
    String phoneNumber;
    String facebookAccount;
    String linkedinAccount;
}
