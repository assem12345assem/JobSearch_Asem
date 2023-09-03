package com.example.jobsearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    @PostMapping("/apply")
    public ResponseEntity<?> apply(long vacancyId,
                                      long resumeId, Authentication auth) {
        return jobApplicationService.apply(vacancyId, resumeId, auth);
    }
    @GetMapping("/vacancies/{applicantId}")
    public ResponseEntity<?> getAllAppliedVacanciesByApplicantId(@PathVariable Long applicantId) {
        return jobApplicationService.getAllAppliedVacanciesByApplicantId(applicantId);
    }
    @GetMapping("/resumes/{vacancyId}")
    public List<ResumeDto> getResumesByVacancyId(@PathVariable Long vacancyId) {
        return jobApplicationService.getResumesByVacancyId(vacancyId);
    }
}
