package com.example.jobsearch.dto;

import com.example.jobsearch.model.Applicant;
import com.example.jobsearch.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
