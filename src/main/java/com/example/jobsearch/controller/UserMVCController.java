package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class UserMVCController {
    private final UserService userService;

    @GetMapping("users/register")
    public String register() {
        return "users/register";
    }

    @PostMapping("users/register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String register(@Valid UserDto userDto)
            throws Exception {
        userService.register(userDto);
        return "redirect:/";
    }

    @GetMapping("users/profile")
    public String profile(Authentication auth, Model model, Model model2) {
        model.addAttribute("user", userService.getUserProfile(auth));
        model2.addAttribute("content", userService.getProfileContent(auth));
        return "users/profile";
    }
}
//    @RequestParam(name = "id") String id,
//    @RequestParam(name = "phoneNumber") String phoneNumber,
//    @RequestParam(name = "userName") String userName,
//    @RequestParam(name = "userType") String userType,
//    @RequestParam(name = "password") String password)

//id, phoneNumber, userName, userType, password