package com.example.jobsearch.controller;

import com.example.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorController {
    private final ErrorService errorService;

    public String error(Exception message) {
        System.out.println(errorService.makeBody(message));
        return "error";
    }
}
