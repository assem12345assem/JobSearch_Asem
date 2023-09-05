package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.AuthService;
import com.example.jobsearch.service.JobApplicationService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyMVCController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final AuthService authService;
    private final JobApplicationService jobApplicationService;

    @GetMapping("/{id}")
    public String vacancyInfo(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("vacancy", vacancyService.findDtoById(id));
        model.addAttribute("user", vacancyService.getVacancyOwner(id));
        model.addAttribute("viewer", authService.getAuthor(auth));
        return "vacancy/info";
    }

    @GetMapping("/edit/{id}")
    public String vacancyEdit (@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("vacancy", vacancyService.findDtoById(id));
        model.addAttribute("user", authService.getAuthor(auth));
        return "vacancy/edit";
    }
    @PostMapping("/edit/{id}")
    public String vacancyEdit (@PathVariable Long id, @ModelAttribute VacancyDto vacancyDto, Authentication auth) {
        vacancyService.edit(id, vacancyDto, auth);
        return "redirect:/vacancy/" + id;
    }
    @GetMapping("/datefix/{id}")
    public String vacancyDateFix(@PathVariable Long id) {
        vacancyService.dateFix(id);
        return "redirect:/vacancy/" + id;
    }

    @GetMapping("/add")
    public String addVacancy(Model model, Authentication auth) {
        model.addAttribute("user", authService.getAuthor(auth));

        model.addAttribute("vacancy", vacancyService.newVacancy(auth));

        return "vacancy/edit";
    }
    @PostMapping("/add")
    public String addVacancy(@ModelAttribute VacancyDto vacancyDto, Authentication auth) {
        vacancyService.edit(vacancyDto.getId(), vacancyDto, auth);
        return "redirect:/vacancy/" + vacancyDto.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteVacancy(@PathVariable Long id, Authentication auth) {
        vacancyService.delete(id);
        UserDto u = authService.getAuthor(auth);
        return "redirect:/auth/profile/" + u.getEmail();
    }

    @GetMapping("/all/view")
    public String viewAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "category", defaultValue = "default") String category,
                          @RequestParam(name = "date", defaultValue = "default") String date,
                          @RequestParam(name = "application", defaultValue = "default") String application,
                          @RequestParam(name = "searchWord", defaultValue = "default") String searchWord,
                          Model model) {
        int pageSize = 6; // Number of vacancies per page
        String sortCriteria = sort;
        var totalVacancies = vacancyService.getAll(sort, pageNumber, pageSize, category, date, application, searchWord);
       int total = vacancyService.getTotalVacanciesCount();
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("vacancies", totalVacancies);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortCriteria", sortCriteria);
        model.addAttribute("category", category);
        model.addAttribute("dates", vacancyService.getDates());
        model.addAttribute("date", date);
        model.addAttribute("applications", jobApplicationService.getCountByVacancy());
        model.addAttribute("application", application);
        model.addAttribute("searchWord", searchWord);
        return "vacancy/all";
    }




}
