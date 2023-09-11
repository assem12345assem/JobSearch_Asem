package com.example.jobsearch.errors.handler;

import jakarta.servlet.http.HttpServletRequest;
import com.example.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    private String notFound(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    private String illegalArgument(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    private String unauthorized(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.value());
        model.addAttribute("reason", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    private String forbidden(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private String methodNotAllowed(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        model.addAttribute("reason", HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }
    @ExceptionHandler(Exception.class)
    private String internalServerError(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("details", request);
        return "/errors/error";
    }

}
