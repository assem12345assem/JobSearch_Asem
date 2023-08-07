package com.example.jobsearch.controller;

import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyMVCController {
    private final VacancyService vacancyService;
    @GetMapping
    public String viewAllVacancies(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllVacancies());
        return "vacancies/all";
    }
    @GetMapping("/{vacancyId}")
    public String getResume(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancy", vacancyService.getVacancyById(vacancyId));
        return "vacancies/vacancy_info";
    }

}
