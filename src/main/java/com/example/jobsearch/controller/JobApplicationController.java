package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    @PostMapping("/apply_for_vacancy")
    public HttpStatus applyForVacancy(@RequestParam("vacancyId") long vacancyId,
                                      @RequestParam("resumeId") long resumeId) {
        jobApplicationService.applyForVacancy(vacancyId, resumeId);
        return HttpStatus.OK;
    }
    @GetMapping("/get_vacancies_by_applicant_id/{applicantId}")
    public List<VacancyDto> getAllAppliedVacanciesByApplicantId(@PathVariable Long applicantId) {
        return jobApplicationService.getAllAppliedVacanciesByApplicantId(applicantId);
    }
    @GetMapping("/resumes_by_vacancy_id/{vacancyId}")
    public List<ResumeDto> getResumesByVacancyId(@PathVariable Long vacancyId) {
        return jobApplicationService.getResumesByVacancyId(vacancyId);
    }
}
