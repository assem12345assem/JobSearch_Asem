package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @Email
    String id;
    String phoneNumber;
    String userName;
    String userType;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, max = 24,
            message = "Length of password must be >= 4 and <= 24")
    String password;
    String photo;
    boolean enabled;
}
