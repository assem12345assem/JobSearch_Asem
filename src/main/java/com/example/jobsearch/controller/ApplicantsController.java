package com.example.jobsearch.controller;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.model.VacancyApplicants;
import com.example.jobsearch.service.ApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantsController {
    private final ApplicantsService service;
    @GetMapping
    public List<VacancyApplicants> getAllApplicants() {
        return service.getAllApplicants();
    }
    @GetMapping("/{vacancyId}")
    public List<VacancyApplicants> getAllApplicantsByVacancyId(@PathVariable int vacancyId) {
        return service.getAllApplicantsByVacancyId(vacancyId);
    }
}
