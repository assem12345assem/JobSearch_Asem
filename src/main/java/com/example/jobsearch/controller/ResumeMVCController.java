package com.example.jobsearch.controller;

import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.service.AuthService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.UtilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeMVCController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final AuthService authService;
    private final UtilService utilService;

    @GetMapping("/info/{id}")
    public String resumeInfo(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("resume", resumeService.findDtoById(id));
        model.addAttribute("user", resumeService.getResumeOwner(id));
        model.addAttribute("viewer", authService.getAuthor(auth));
        return "resume/info";
    }
    @GetMapping("/view/by_employer/{id}")
    public String resumeByEmployer(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("resume", resumeService.findDtoById(id));
        model.addAttribute("user", resumeService.getResumeOwner(id));
        model.addAttribute("viewer", authService.getAuthor(auth));
        return "resume/info";
    }
    @PostMapping("/add")
    public String addResume(Model model, Authentication auth) {
        System.out.println("addResume");
        model.addAttribute("user", authService.getAuthor(auth));
        ResumeDto r = resumeService.newResume(auth);
        model.addAttribute("resume", r);

        return "redirect:/resume/edit/" + r.getId();
    }
    @GetMapping("/edit/{id}")
    public String resumeEdit (@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("resume", resumeService.findDtoById(id));
        model.addAttribute("user", authService.getAuthor(auth));
        return "resume/edit";
    }
    @PostMapping("/edit/{id}")
    public String resumeEdit (@PathVariable Long id, @ModelAttribute ResumeDto resume, Authentication auth) {
        resumeService.edit(id, resume, auth);
        return "redirect:/resume/info/" + id;
    }


    @GetMapping("/edit/datefix/{id}")
    public String resumeDateFix(@PathVariable Long id) {
        resumeService.dateFix(id);
        return "redirect:/resume/info/" + id;
    }
    @PostMapping("/edit/add_one_work/{resumeId}")
    public String addOneWork(@PathVariable Long resumeId, @ModelAttribute WorkExperienceDto workExperienceDto) {
        resumeService.addOneWork(resumeId, workExperienceDto);
        return "redirect:/resume/edit/" + resumeId;
    }
    @PostMapping("/edit/add_one_edu/{resumeId}")
    public String addOneEducation(@PathVariable Long resumeId, @Valid @ModelAttribute EducationDto educationDto, BindingResult bindingResult) {
        resumeService.addOneEducation(resumeId, educationDto);
        return "redirect:/resume/edit/" + resumeId;
    }
    @GetMapping("/edit/delete_work/{workExperienceId}")
    public String deleteOneWork(@PathVariable Long workExperienceId) {
        Long resumeId = resumeService.deleteOneWork(workExperienceId);
        return "redirect:/resume/edit/" + resumeId;
    }
    @GetMapping("/edit/delete_education/{educationId}")
    public String deleteOneEducation(@PathVariable Long educationId) {
        Long resumeId = resumeService.deleteOneEducation(educationId);
        return "redirect:/resume/edit/" + resumeId;
    }

    @GetMapping("/edit/delete/{id}")
    public String deleteResume(@PathVariable Long id, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        resumeService.delete(id);
        return "redirect:/auth/profile/" + u.getEmail();
    }

    @GetMapping("/all/view")
    public String viewAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "category", defaultValue = "default") String category,
                          @RequestParam(name = "searchWord", defaultValue = "default") String searchWord,
                          Model model) {
        int pageSize = 6; // Number of vacancies per page

        model.addAttribute("resumes", resumeService.getAll(sort, pageNumber, pageSize, category, searchWord));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", utilService.totalPagesCounter(resumeService.getTotalResumesCount(), pageSize));
        model.addAttribute("sortCriteria", sort);
        model.addAttribute("category", category);
        model.addAttribute("searchWord", searchWord);
        return "resume/all";
    }

}
