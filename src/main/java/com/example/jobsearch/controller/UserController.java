package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get_user_by_phone_number/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return userService.getOptionalUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> printUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/if_user_exists/{email}")
    public boolean ifUserExists(@PathVariable String email) {
        return userService.ifUserExists(email);
    }

    @PostMapping("/create_user")
    public HttpStatus createUser(UserDto userDto) {
        if (!userService.ifUserExists(userDto.getId())) {
            userService.createUser(userDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("User already exists");
    }

    @PostMapping("/edit_user")
    public HttpStatus editUser(@RequestBody UserDto userDto, Authentication auth) {
        if (userService.ifUserExists(userDto.getId())) {
            userService.editUser(userDto, auth);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("User does not exist.");

    }

    @PostMapping("/upload_photo")
    public HttpStatus uploadPhoto(String email, MultipartFile file, Authentication auth) {
        userService.uploadUserPhoto(email, file, auth);
        return HttpStatus.OK;
    }
}
