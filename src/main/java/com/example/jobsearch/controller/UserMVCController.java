package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class UserMVCController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("users/register")
    public String register() {
        return "users/register";
    }

    @PostMapping("users/register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String register(@Valid UserDto userDto) throws Exception {
        userService.register(userDto);
        return "redirect:/";
    }

    @GetMapping("users/profile/{userId}")
    public String profile(@PathVariable String userId, Model model, Model model2, Model model3) throws Exception {
        model.addAttribute("profile", profileService.getUserProfile(userId));
        model2.addAttribute("items", profileService.getProfileContent(userId));
        model3.addAttribute("user", userService.getUserByEmail(userId));
        return "users/profile";
    }

    @GetMapping("users/login")
    public String login() {
        return "users/login";
    }

    @PostMapping("users/login")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String login(@Valid UserDto userDto) {
        String userId = userService.login(userDto);
        return "redirect:/users/profile/" + userId;
    }

    @GetMapping("users/profile/edit/{userId}")
    public String editProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.getUserByEmail(userId));

        return "users/edit";
    }

    @PostMapping("users/profile/edit/{userid}")
    public String editProfile(@PathVariable String userId, @RequestParam(name = "phoneNumber", required = false, defaultValue = "") String phoneNumber, @RequestParam(name = "userName", required = false, defaultValue = "") String userName, @RequestParam(name = "password", required = false, defaultValue = "") String password, @RequestParam(name = "photo", required = false, defaultValue = "") MultipartFile file, @RequestParam(name = "companyName", required = false, defaultValue = "") String companyName, @RequestParam(name = "firstName", required = false, defaultValue = "") String firstName, @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName, @RequestParam(name = "DOB", required = false, defaultValue = "") LocalDate date) {
        profileService.edit(userId, phoneNumber, userName, password, file, companyName, firstName, lastName, date);
        return "users/profile/" + userId;
    }

}
