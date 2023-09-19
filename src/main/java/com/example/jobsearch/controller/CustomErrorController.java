package com.example.jobsearch.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public String errorPage(HttpServletRequest request, Model model) {
        int statusCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", statusCode);
        model.addAttribute("reason", "reason.forbidden");
        model.addAttribute("details", request);
        return "errors/error";
    }


}
