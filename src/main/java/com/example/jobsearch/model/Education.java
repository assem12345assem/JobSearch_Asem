package com.example.jobsearch.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Education {
    Long id;
    Long resumeId;
    String education;
    String schoolName;
    LocalDate startDate;
    LocalDate graduationDate;

}
