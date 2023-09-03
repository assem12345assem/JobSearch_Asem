package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.VacancyDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.service.VacancyService;
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
    public String viewAll(Model model) {
        model.addAttribute("vacancies", vacancyService.getAll());
        return "vacancy/all";
    }

    @GetMapping("/all/by_date")
    public String viewAllByDate(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllByDate());
        return "vacancy/all";
    }
    @GetMapping("/all/by_date_reversed")
    public String viewAllByDateReversed(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllByDateReversed());
        return "vacancy/all";
    }
    @GetMapping("/all/by_salary")
    public String viewAllBySalary(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllBySalary());
        return "vacancy/all";
    }
    @PostMapping("/all/search")
    public String searchVacancy(@RequestParam(name="search") String search, Model model ) {
        model.addAttribute("vacancies", vacancyService.searchResult(search));
        return "vacancy/all";

    }


    @GetMapping("/all/by_category")
    public String filterByCategory(@RequestParam(name="category") String category, Model model ){
        model.addAttribute("vacancies", vacancyService.filterByCategory(category));
        return "vacancy/all";
    }

}
