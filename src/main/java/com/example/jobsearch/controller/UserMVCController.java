package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public String register(@Valid UserDto userDto)
            throws Exception {
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
    public String login(@Valid UserDto userDto)
            throws Exception {
        String userId = userService.login(userDto);
        return "redirect:/users/profile/" + userId;
    }

    @GetMapping("users/profile/edit/{userid}")
    public String editProfile(@PathVariable String userId) {
        return "users/edit";
    }
//
//    @PostMapping("users/profile/edit/{userid}")
//    public String editProfile(@PathVariable String userId) {
//
//        return "users/profile/" + userId;
//    }

}
//    @RequestParam(name = "id") String id,
//    @RequestParam(name = "phoneNumber") String phoneNumber,
//    @RequestParam(name = "userName") String userName,
//    @RequestParam(name = "userType") String userType,
//    @RequestParam(name = "password") String password)

//id, phoneNumber, userName, userType, password