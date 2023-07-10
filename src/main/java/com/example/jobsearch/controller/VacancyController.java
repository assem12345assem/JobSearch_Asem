package com.example.jobsearch.controller;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService service;

    @GetMapping
    public List<Vacancy> getAllVacancies() {
        return service.getAllVacancies();
    }
    @GetMapping("/{userId}")
    public List<Vacancy> getVacanciesByUserId(@PathVariable int userId) {
        return service.getVacanciesByUserId(userId);
    }
}
