package com.example.jobsearch.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobApplication {
    Long id;
    Long vacancyId;
    Long resumeId;
    LocalDateTime dateTime;

}
