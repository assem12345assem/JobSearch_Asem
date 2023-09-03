package com.example.jobsearch.errors.handler;

import com.example.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementHandler(NoSuchElementException exception) {
        return new ResponseEntity<>(errorService.makeBody(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(errorService.makeBody(exception.getBindingResult()), HttpStatus.BAD_REQUEST);
    }


}
