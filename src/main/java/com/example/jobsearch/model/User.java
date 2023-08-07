package com.example.jobsearch.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class User {
    String id;
    String phoneNumber;
    String userName;
    String userType;
    String password;
    String photo;
    boolean enabled;
}
