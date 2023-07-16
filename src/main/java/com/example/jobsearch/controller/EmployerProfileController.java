package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.EmployerProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerProfileController {
    private final EmployerProfileService employerProfileService;

    @PostMapping("/create_employer")
    public HttpStatus createEmployer(@RequestBody EmployerDto employerDto) {
        log.warn("Created new employer: {}", employerDto.getCompanyName());
        if (!employerProfileService.ifEmployerExists(employerDto.getUserId())) {
            employerProfileService.createEmployer(employerDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Employer already exists");
    }

    @PostMapping("/edit_employer")
    public HttpStatus editEmployer(@RequestBody EmployerDto employerDto) {
        log.warn("Edited employer: {}", employerDto.getCompanyName());
        if (employerProfileService.ifEmployerExists(employerDto.getUserId())) {
            employerProfileService.editEmployer(employerDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Employer does not exist");
    }

    @PostMapping("/create_vacancy")
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        employerProfileService.createVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @PostMapping("/edit_vacancy")
    public HttpStatus editVacancy(@RequestBody VacancyDto vacancyDto) {
        employerProfileService.editVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete_vacancy")
    public HttpStatus deleteVacancy(@RequestBody VacancyDto vacancyDto) {
        employerProfileService.deleteVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @GetMapping("/view_all_resumes")
    public List<ResumeDto> viewAllResumes() {
        return employerProfileService.getAllResumes();
    }
    @GetMapping("/get_resume_by_title/{title}")
    public List<ResumeDto> getResumeByResumeTitle(@PathVariable String title) {
        return employerProfileService.getResumeByResumeTitle(title);
    }
    @GetMapping("/get_resume_by_category/{category}")
    public List<ResumeDto> getResumesByCategoryName(@PathVariable String category) {
        return employerProfileService.getResumesByCategoryName(category);
    }
    @GetMapping("/get_resume_by_category_id/{id}")
    public List<ResumeDto> getAllResumesByCategoryId(@PathVariable long id) {
        return employerProfileService.getAllResumesByCategoryId(id);
    }
    @GetMapping("/resumes_by_vacancy_id/{vacancyId}")
    public List<ResumeDto> getResumesByVacancyId(@PathVariable Long vacancyId) {
        return employerProfileService.getAllResumesByVacancyId(vacancyId);
    }
    @GetMapping("/get_applicant_by_id/{applicantId}")
    public ApplicantDto getApplicantById(@PathVariable Long applicantId) {
        return employerProfileService.getApplicantById(applicantId);
    }
}
