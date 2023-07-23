package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String id;
    String phoneNumber;
    String userType;
    String password;
    String photo;
    boolean enabled;
    Long roleId;
}
