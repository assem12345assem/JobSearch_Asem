package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class JobApplication {
    private long id;
    private long vacancyId;
    private long resumeId;
    private LocalDateTime dateTime;
}
