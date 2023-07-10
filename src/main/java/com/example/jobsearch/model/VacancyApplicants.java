package com.example.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class VacancyApplicants {
    private int id;
    private int applicantUserId;
    private int resumeId;
    private int vacancyId;
    private LocalDateTime dateTimeApplication;
}
