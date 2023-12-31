package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final UtilService utilService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.register(userDto);
            return "redirect:/auth/login";
        }
        return "auth/register";


    }

    @GetMapping("/error_login")
    public String errorLogin() {
        return "auth/error_login";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @GetMapping("/profile/company/{email}")
    public String companyProfile(@PathVariable String email, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, Model model) {
        int pageSize = 3; // Number of vacancies per page
        model.addAttribute("company", vacancyService.getCompanyDto(email));
        model.addAttribute("myList", vacancyService.findSummaryByEmployerEmail(email, pageNumber, pageSize));
        model.addAttribute("totalPages", utilService.totalPagesCounter(vacancyService.getEmployerVacancyCount(email), pageSize));
        model.addAttribute("pageNumber", pageNumber);
        return "vacancy/profile";
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                          Model model, Authentication auth) {
        int pageSize = 3; // Number of vacancies per page

        model.addAttribute("user", userService.getUserDtoLocalStorage(username));
        ResponseEntity<?> responseEntity = userService.getProfileLocalStorage(username);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Object responseBody = responseEntity.getBody();
            if (responseBody instanceof ApplicantDto) {
                model.addAttribute("applicantProfile", responseBody);
                Long applicantId = ((ApplicantDto) responseBody).getId();
                model.addAttribute("myList", resumeService.findSummaryByApplicantId(applicantId, pageNumber, pageSize));
                model.addAttribute("totalPages", utilService.totalPagesCounter(resumeService.findAllByApplicant(auth).size(), pageSize));
            }
            if (responseBody instanceof EmployerDto) {
                model.addAttribute("employerProfile", responseBody);
                Long employerId = ((EmployerDto) responseBody).getId();
                model.addAttribute("myList", vacancyService.findSummaryByEmployerId(employerId, pageNumber, pageSize));
                model.addAttribute("totalPages", utilService.totalPagesCounter(vacancyService.getEmployerVacancyCount(employerId), pageSize));
            }
        }
        model.addAttribute("new_message_number", jobApplicationService.getNew(auth));
        model.addAttribute("pageNumber", pageNumber);

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
        }
        return "auth/edit";
    }

    @PostMapping(path = "/edit/{userLocalStorage}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String edit(@PathVariable String userLocalStorage, EditProfileDto editProfileDto) {
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

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "auth/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(HttpServletRequest request, Model model) {
        userService.makeResetPasswdLink(request);
        model.addAttribute("message", "http://localhost:8089/auth/reset_password");
        request.getSession().setAttribute("email", request.getParameter("email"));
        return "auth/forgot_password";
    }

    @GetMapping("/reset_password")
    public String resetPasswordForm(@SessionAttribute String email, Model model) {

        try {
            model.addAttribute("username", email);
            String token = userService.getToken(email);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@SessionAttribute String email, HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            var user = userService.getByResetPasswdToken(email, token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");

        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token.");
        }
        return "redirect:/auth/message";
    }

    @GetMapping("/message")
    public String changedPage() {
        return "auth/message";
    }

}
