package com.example.jobsearch.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ResumeCreationRequestDto {
    private ResumeDto resumeDto;
    private List<InputWorkExperienceDto> workExperienceDto;
    private List<InputEducationDto> educationDto;
    private InputContactInfoDto contactInfoDto;
}
