package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyMVCController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final AuthService authService;
    private final JobApplicationService jobApplicationService;
    private final UtilService utilService;

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
    @GetMapping("/edit/datefix/{id}")
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
    @GetMapping("/edit/delete/{id}")
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
        var totalVacancies = vacancyService.getAll(sort, pageNumber, pageSize, category, date, application, searchWord);
       int total = vacancyService.getTotalVacanciesCount();
        int totalPages = utilService.totalPagesCounter(total, pageSize);

        model.addAttribute("vacancies", totalVacancies);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortCriteria", sort);
        model.addAttribute("category", category);
        model.addAttribute("dates", vacancyService.getDates());
        model.addAttribute("date", date);
        model.addAttribute("applications", jobApplicationService.getCountByVacancy());
        model.addAttribute("application", application);
        model.addAttribute("searchWord", searchWord);
        return "vacancy/all";
    }
    @GetMapping("/all/companies")
    public String companies(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                            Model model) {
        int pageSize = 3; //companies per page

       int total = utilService.totalPagesCounter(vacancyService.getCompanyDtoSize(), pageSize);
        model.addAttribute("companies", vacancyService.makeCompanyDtos(pageNumber, pageSize));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", total);
        return "vacancy/companies";

    }
}