package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
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

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserMVCController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final JobApplicationService jobApplicationService;
    private final AuthService authService;
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
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "auth/forgot_password";
    }

    @GetMapping("reset_password")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        try {
            userService.getByResetPasswdToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("reset_password")
    public String resetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            var user = userService.getByResetPasswdToken(token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("message", "Invalid token.");
        }
        return "auth/message";
    }

}
