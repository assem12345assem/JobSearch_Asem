package com.example.jobsearch.dto;


import lombok.Data;

import java.util.List;

@Data
public class ResumeDto {
    private long id;
    private ApplicantDto applicant;
    private String resumeTitle;
    private CategoryDto category;
    private Integer expectedSalary;
    private boolean isActive;
    private boolean isPublished;
    private List<WorkExperienceDto> workExperienceList;
    private List<EducationDto> educationList;
    private ContactInfoDto contactInfo;
}
