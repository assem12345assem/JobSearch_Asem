package com.example.jobsearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ApplicantService applicantService;
    private final EmployerService employerService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final CategoryService categoryService;
    private final ContactInfoService contactInfoService;
    private final EducationService educationService;
    private final UserService userService;
    private final WorkExperienceService workExperienceService;
}
