package com.example.jobsearch.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resume {
    Long id;
    Long applicantId;
    String resumeTitle;
    Long categoryId;
    Integer expectedSalary;
    boolean isActive;
    boolean isPublished;

}
