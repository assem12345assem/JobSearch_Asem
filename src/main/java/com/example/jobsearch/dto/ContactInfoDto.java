package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfoDto {
    Long id;
    @NotNull
    Long resumeId;
    String telegram;
    @Email
    String email;
    String phoneNumber;
    String facebookAccount;
    String linkedinAccount;
}
