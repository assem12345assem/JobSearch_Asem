package com.example.demo.controller;

import com.example.demo.dto.EducationDto;
import com.example.demo.dto.ResumeDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.WorkExperienceDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.ResumeService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeMVCController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{id}")
    public String resumeInfo(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("resume", resumeService.findDtoById(id));
        model.addAttribute("user", resumeService.getResumeOwner(id));
        model.addAttribute("viewer", authService.getAuthor(auth));
        return "resume/info";
    }
    @GetMapping("/edit/{id}")
    public String resumeEdit (@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("resume", resumeService.findDtoById(id));
        model.addAttribute("user", authService.getAuthor(auth));
        return "resume/edit";
    }
    @PostMapping("/edit/{id}")
    public String resumeEdit (@PathVariable Long id, @ModelAttribute ResumeDto resumeDto, Authentication auth) {
        resumeService.edit(id, resumeDto, auth);
        return "redirect:/resume/" + id;
    }
    @GetMapping("/datefix/{id}")
    public String resumeDateFix(@PathVariable Long id) {
        resumeService.dateFix(id);
        return "redirect:/resume/" + id;
    }
    @PostMapping("/add_one_work/{resumeId}")
    public String addOneWork(@PathVariable Long resumeId, @ModelAttribute WorkExperienceDto workExperienceDto) {
        resumeService.addOneWork(resumeId, workExperienceDto);
        return "redirect:/resume/edit/" + resumeId;
    }
    @PostMapping("/add_one_edu/{resumeId}")
    public String addOneEducation(@PathVariable Long resumeId, @ModelAttribute EducationDto educationDto) {
        resumeService.addOneEducation(resumeId, educationDto);
        return "redirect:/resume/edit/" + resumeId;
    }
    @GetMapping("/delete_work/{workExperienceId}")
    public String deleteOneWork(@PathVariable Long workExperienceId) {
        Long resumeId = resumeService.deleteOneWork(workExperienceId);
        return "redirect:/resume/edit/" + resumeId;
    }
    @GetMapping("/delete_education/{educationId}")
    public String deleteOneEducation(@PathVariable Long educationId) {
        Long resumeId = resumeService.deleteOneEducation(educationId);
        return "redirect:/resume/edit/" + resumeId;
    }
    @GetMapping("/add")
    public String addResume(Model model, Authentication auth) {
        model.addAttribute("user", authService.getAuthor(auth));

        model.addAttribute("resume", resumeService.newResume(auth));

        return "resume/edit";
    }
    @PostMapping("/add")
    public String addResume(@ModelAttribute ResumeDto resumeDto, Authentication auth) {
        resumeService.edit(resumeDto.getId(), resumeDto, auth);
        return "redirect:/resume/" + resumeDto.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        resumeService.delete(id);
        return "redirect:/auth/profile" + u.getEmail();
    }

    @GetMapping("/all/view")
    public String viewAll(Model model) {
        model.addAttribute("resumes", resumeService.getAll());
        return "resume/all";
    }
    @PostMapping("/all/search")
    public String searchResume(@RequestParam(name="search") String search, Model model ) {
        model.addAttribute("resumes", resumeService.searchResult(search));
        return "resume/all";

    }
    @GetMapping("/all/by_date_reversed")
    public String viewAllByDateReversed(Model model) {
        model.addAttribute("resumes", resumeService.getAllByDateReversed());
        return "resume/all";
    }




    @GetMapping("/all/by_category")
    public String filterByCategory(@RequestParam(name="category") String category, Model model ){
        model.addAttribute("resumes", resumeService.filterByCategory(category));
        return "resume/all";
    }
}
