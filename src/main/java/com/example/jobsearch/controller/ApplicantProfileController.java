package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.ApplicantProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantProfileController {
    private final ApplicantProfileService applicantProfileService;
    @PostMapping("/create_applicant")
    public HttpStatus createApplicant(@RequestBody ApplicantDto applicantDto) {
        log.warn("Created new applicant: {} {}", applicantDto.getFirstName(), applicantDto.getLastName());
        if (!applicantProfileService.ifApplicantExists(applicantDto.getUserId())) {
            applicantProfileService.createApplicant(applicantDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Applicant already exists");
    }
    @PostMapping("/edit_applicant")
    public HttpStatus editApplicant(@RequestBody ApplicantDto applicantDto) {
        log.warn("Edited applicant: {} {}", applicantDto.getFirstName(), applicantDto.getLastName());
        if (applicantProfileService.ifApplicantExists(applicantDto.getUserId())) {
            applicantProfileService.editApplicant(applicantDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Applicant does not exist");
    }
    @PostMapping("/create_resume")
    public HttpStatus createResume(@RequestBody ResumeDto resumeDto) {
        applicantProfileService.createResume(resumeDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_resume")
    public HttpStatus editResume(@RequestBody ResumeDto resumeDto) {
        applicantProfileService.editResume(resumeDto);
        return HttpStatus.OK;
    }
    @DeleteMapping("/delete_resume")
    public HttpStatus deleteResume(@RequestBody ResumeDto resumeDto) {
        applicantProfileService.deleteResume(resumeDto);
        return HttpStatus.OK;
    }
    @GetMapping("/all_vacancies")
    public List<VacancyDto> getAllVacancies() {
        return applicantProfileService.getAllVacancies();
    }
    @GetMapping("/all_vacancies_by_category/{category}")
    public List<VacancyDto> getAllVacanciesByCategory(@PathVariable String category) {
        return applicantProfileService.getAllVacanciesByCategory(category);
    }
    @PostMapping("/apply_for_vacancy")
    public HttpStatus applyForVacancy(@RequestParam("vacancyId") long vacancyId,
                                      @RequestParam("resumeId") long resumeId) {
        applicantProfileService.applyForVacancy(vacancyId, resumeId);
        return HttpStatus.OK;
    }
    @GetMapping("/get_employer_by_id/{id}")
    public EmployerDto getEmployerById(@PathVariable Long id) {
        return applicantProfileService.getEmployerById(id);
    }
    @GetMapping("/get_employer_by_company_name/{companyName}")
    public List<EmployerDto> getEmployerByCompanyName(@PathVariable String companyName) {
        return applicantProfileService.getEmployerByCompanyName(companyName);
    }
}
