package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    @GetMapping
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }
    @GetMapping("/all_vacancies_by_category/{category}")
    public List<VacancyDto> getAllVacanciesByCategory(@PathVariable String category) {
        return vacancyService.getAllVacanciesByCategory(category);
    }
    @PostMapping("/apply_for_vacancy")
    public HttpStatus applyForVacancy(@RequestParam("vacancyId") long vacancyId,
                                      @RequestParam("resumeId") long resumeId) {
        vacancyService.applyForVacancy(vacancyId, resumeId);
        return HttpStatus.OK;
    }
    @PostMapping("/create_vacancy")
    public HttpStatus createVacancy(VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_vacancy")
    public HttpStatus editVacancy(VacancyDto vacancyDto) {
        vacancyService.editVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @GetMapping("/get_vacancies_by_applicant_id/{applicantId}")
    public List<VacancyDto> getAllAppliedVacanciesByApplicantId (@PathVariable Long applicantId) {
        return vacancyService.getAllAppliedVacanciesByApplicantId(applicantId);
    }

    @DeleteMapping("/delete_vacancy")
    public HttpStatus deleteVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.deleteVacancy(vacancyDto);
        return HttpStatus.OK;
    }

}
