package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.AuthService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String viewAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "category", defaultValue = "default") String category,
                          Model model) {
        int pageSize = 6; // Number of vacancies per page
        String sortCriteria = sort;
        int totalVacancies = vacancyService.getTotalVacanciesCount();
        int totalPages = (int) Math.ceil((double) totalVacancies / pageSize); // Calculate total pages
        model.addAttribute("vacancies", vacancyService.getAll(sort, pageNumber, pageSize, category));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortCriteria", sortCriteria);
        model.addAttribute("category", category);
        return "vacancy/all";
    }


//    @GetMapping("/all/by_date")
//    public String viewAllByDate(Model model) {
//        model.addAttribute("vacancies", vacancyService.getAllByDate());
//        return "vacancy/all";
//    }
//    @GetMapping("/all/by_date_reversed")
//    public String viewAllByDateReversed(Model model) {
//        model.addAttribute("vacancies", vacancyService.getAllByDateReversed());
//        return "vacancy/all";
//    }

    @GetMapping("/all/by_date_reversed")
    public String viewAllByDateReversed(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, Model model) {
        int pageSize = 6; // Number of vacancies per page

        int totalVacancies = vacancyService.getTotalVacanciesCount();
        int totalPages = (int) Math.ceil((double) totalVacancies / pageSize); // Calculate total pages

        model.addAttribute("vacancies", vacancyService.getAllByDateReversed("dateTime", pageNumber, pageSize));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        return "vacancy/all";
    }
//    @GetMapping("/all/by_salary")
//    public String viewAllBySalary(Model model) {
//        model.addAttribute("vacancies", vacancyService.getAllBySalary());
//        return "vacancy/all";
//    }
//    @PostMapping("/all/search")
//    public String searchVacancy(@RequestParam(name="search") String search, Model model ) {
//        model.addAttribute("vacancies", vacancyService.searchResult(search));
//        return "vacancy/all";
//
//    }
//
//
//    @GetMapping("/all/by_category")
//    public String filterByCategory(@RequestParam(name="category") String category, Model model ){
//        model.addAttribute("vacancies", vacancyService.filterByCategory(category));
//        return "vacancy/all";
//    }

}
