package com.example.jobsearch.controller;

import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.service.EmployerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerProfileController {
    private final EmployerProfileService employerProfileService;
    @GetMapping
    public List<EmployerDto> getAllEmployers() {
        return employerProfileService.getAllEmployers();}
    @GetMapping("/{userId}")
    public ResponseEntity<?> getEmployerByUserId(@PathVariable String userId) {
        return employerProfileService.getEmployerByUserId(userId);
    }
    @PostMapping("/create_employer")
    public ResponseEntity<?> createEmployer(@RequestBody @Valid EmployerDto employerDto) {
        return employerProfileService.createEmployer(employerDto);
    }

    @PostMapping("/edit_employer")
    public ResponseEntity<?> editEmployer(@RequestBody @Valid EmployerDto employerDto, Authentication auth) {
           return employerProfileService.editEmployer(employerDto, auth);
    }
    @GetMapping("/get_employer_by_id/{id}")
    public ResponseEntity<?> getEmployerById(@PathVariable Long id) {
        return employerProfileService.getEmployerById(id);
    }
    @GetMapping("/get_employer_by_company_name/{companyName}")
    public ResponseEntity<?> getEmployerByCompanyName(@PathVariable String companyName) {
        return employerProfileService.getEmployerByCompanyName(companyName);
    }

}
