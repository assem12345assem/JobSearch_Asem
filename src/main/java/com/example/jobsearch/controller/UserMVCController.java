package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.*;
import com.example.jobsearch.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserMVCController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final JobApplicationService jobApplicationService;
    private final AuthService authService;

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String register(@Valid @ModelAttribute UserDto userDto) throws Exception {
        userService.register(userDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        model.addAttribute("user", userService.getUserDtoLocalStorage(u.getEmail()));
        ResponseEntity<?> responseEntity = userService.getProfileLocalStorage(u.getEmail());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Object responseBody = responseEntity.getBody();
            if (responseBody instanceof ApplicantDto) {
                model.addAttribute("applicantProfile", responseBody);
                Long applicantId = ((ApplicantDto) responseBody).getId();
                model.addAttribute("myList", resumeService.findSummaryByApplicantId(applicantId));
            }
            if (responseBody instanceof EmployerDto) {
                model.addAttribute("employerProfile", responseBody);
                Long employerId = ((EmployerDto) responseBody).getId();
                model.addAttribute("myList", vacancyService.findSummaryByEmployerId(employerId));
            }
        }
        model.addAttribute("new_message_number", jobApplicationService.getNew(auth));
        return "auth/profile";
    }

    @GetMapping("/edit/{userLocalStorage}")
    public String edit(@PathVariable String userLocalStorage, Model model) {
        model.addAttribute("user", userService.getUserDtoLocalStorage(userLocalStorage));
        ResponseEntity<?> responseEntity = userService.getProfileLocalStorage(userLocalStorage);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Object responseBody = responseEntity.getBody();

            if (responseBody instanceof ApplicantDto) {
                model.addAttribute("applicantProfile", responseBody);
            }
            if (responseBody instanceof EmployerDto) {
                model.addAttribute("employerProfile", responseBody);
            }
        }        return "auth/edit";
    }

    @PostMapping(path = "/edit/{userLocalStorage}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String edit(@PathVariable String userLocalStorage, EditProfileDto editProfileDto)  {
        userService.editProfile(userLocalStorage, editProfileDto);
        return "redirect:/auth/profile/" + userLocalStorage;
    }

    @GetMapping("/images/{email}")
    public ResponseEntity<?> getImageByName(@PathVariable String email) {
        return userService.getPhoto(email);
    }

    @PostMapping("/images/upload/{userLocalStorage}")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String uploadPhoto(@PathVariable String userLocalStorage, @RequestBody MultipartFile file) {
        userService.uploadUserPhoto(userLocalStorage, file);
        return "redirect:/auth/edit/" + userLocalStorage;
    }

}
