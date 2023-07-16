package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
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
    public HttpStatus createUser(@PathVariable UserDto userDto) {
        log.warn("Created new user: {}", userDto.getId());
        if(!userService.ifUserExists(userDto.getId())) {
            userService.createUser(userDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("User already exists");
    }
    @PostMapping("/edit_user")
    public HttpStatus editUser(@PathVariable UserDto userDto) {
        log.warn("Edited user: {}", userDto.getId());
        if(userService.ifUserExists(userDto.getId())) {
            userService.editUser(userDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("User does not exist.");

    }
    //delete user
    //search by phone, type, etc.
}
