package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/delete_vacancy")
    public HttpStatus deleteVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.deleteVacancy(vacancyDto);
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
