package com.example.jobsearch.errors.handler;

import com.example.jobsearch.errors.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    private String notFound(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "reason.not_found");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private String illegalArgument(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", "reason.bad_request");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    private String badRequest(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", "reason.bad_request");
        model.addAttribute("details", request);
        return "errors/error";
    }
    @ExceptionHandler(NoSuchElementException.class)
    private String noSuchElement(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", "reason.no_such_element");
        model.addAttribute("details", request);
        return "errors/error";
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    private String unauthorized(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.value());
        model.addAttribute("reason", "reason.unauthorized");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    private String accessDenied(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", "reason.forbidden");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    private String forbidden(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", "reason.forbidden");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private String pageNotFound(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "reason.page_not_found");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    private String otherException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", "reason.forbidden");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpClientErrorException.MethodNotAllowed.class)
    private String methodNotAllowed(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        model.addAttribute("reason", "reason.method");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    private String internalServerError(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "reason.server_error");
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpClientErrorException.UnsupportedMediaType.class)
    private String unsupportedMediaType(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        model.addAttribute("reason", "reason.media");
        model.addAttribute("details", request);
        return "errors/error";
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    private String handleUsernameNotFoundException(HttpServletRequest request, Model model, UsernameNotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "reason.username_not_found");
        model.addAttribute("details", ex.getMessage());
        return "errors/error";
    }
}
