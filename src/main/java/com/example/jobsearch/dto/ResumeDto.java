package com.example.jobsearch.dto;

import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResumeDto {
    private Long id;
    private ApplicantDto profile;
    private String resumeTitle;
    private String category;
    private Integer expectedSalary;
    private boolean isActive;
    private boolean isPublished;
    private List<@Valid EducationDto> eduList;
    private List<WorkExperienceDto> workList;
    private ContactInfoDto contact;
    private LocalDateTime dateTime;


    @Override
    public String toString() {
        return "ResumeDto{" +
                "profile=" + profile +
                ", resumeTitle='" + resumeTitle + '\'' +
                ", category='" + category + '\'' +
                ", expectedSalary=" + expectedSalary +
                ", isActive=" + isActive +
                ", isPublished=" + isPublished +
                ", eduList=" + eduList +
                ", workList=" + workList +
                ", contact=" + contact +
                '}';
    }
}
