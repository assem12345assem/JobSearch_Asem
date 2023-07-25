package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/employer/create_vacancy")
    public HttpStatus createVacancy(VacancyDto vacancyDto, Authentication auth) {
        vacancyService.createVacancy(vacancyDto, auth);
        return HttpStatus.OK;
    }

    @PostMapping("/employer/edit_vacancy")
    public HttpStatus editVacancy(VacancyDto vacancyDto, Authentication auth) {
        vacancyService.editVacancy(vacancyDto, auth);
        return HttpStatus.OK;
    }

    @DeleteMapping("/employer/delete_vacancy")
    public HttpStatus deleteVacancy(@RequestBody VacancyDto vacancyDto, Authentication auth) {
        vacancyService.deleteVacancy(vacancyDto, auth);
        return HttpStatus.OK;
    }

    @GetMapping("sort_by_date")
    public ResponseEntity<?> sortedByDate() {
        return vacancyService.sortedListVacancies("by_date");
    }

    @GetMapping("sort_by_category")
    public ResponseEntity<?> sortedByCategory() {
        return vacancyService.sortedListVacancies("by_category");
    }

    @GetMapping("sort_by_salary")
    public ResponseEntity<?> sortedBySalary() {
        return vacancyService.sortedListVacancies("by_salary");
    }

    @GetMapping("sort_by_name")
    public ResponseEntity<?> sortedByName() {
        return vacancyService.sortedListVacancies("by_name");
    }

}
