package com.example.jobsearch.controller;


import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @GetMapping
    public String getMain(Model model) {
        model.addAttribute("vacancies", vacancyService.findSummaryForMain());
        model.addAttribute("resumes", resumeService.findSummaryForMain());
        return "index";
    }


}
