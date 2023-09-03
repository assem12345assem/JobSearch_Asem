package com.example.jobsearch.controller;

import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> getAllVacanciesByCategory(@PathVariable String category) {
        return vacancyService.getAllVacanciesByCategory(category);
    }
    @GetMapping("/all_by_employer/{employerId}")
    public ResponseEntity<?> getAllVacanciesByEmployerId(@PathVariable Long employerId) {
        return vacancyService.findAllVacanciesByEmployerId(employerId);
    }
    @PostMapping("/employer/create_vacancy")
    public ResponseEntity<?> createVacancy(VacancyDto vacancyDto, Authentication auth) {
        return vacancyService.createVacancy(vacancyDto, auth);
    }

    @PostMapping("/employer/edit_vacancy")
    public ResponseEntity<?> editVacancy(VacancyDto vacancyDto, Authentication auth) {
        return vacancyService.editVacancy(vacancyDto, auth);
    }
    @DeleteMapping("/employer/delete_vacancy")
    public ResponseEntity<?> deleteVacancy(@RequestBody VacancyDto vacancyDto, Authentication auth) {
        return vacancyService.deleteVacancy(vacancyDto, auth);
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
