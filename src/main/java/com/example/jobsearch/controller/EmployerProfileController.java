package com.example.jobsearch.controller;

import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.service.EmployerProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerProfileController {
    private final EmployerProfileService employerProfileService;
    @GetMapping
    public List<EmployerDto> getAllEmployers() {
        return employerProfileService.getAllEmployers();}
    @GetMapping("/{userId}")
    public EmployerDto getEmployerByUserId(@PathVariable String email) {
        return employerProfileService.getEmployerByUserId(email);
    }
    @PostMapping("/create_employer")
    public HttpStatus createEmployer(@RequestBody EmployerDto employerDto) {
        log.warn("Created new employer: {}", employerDto.getCompanyName());
        if (!employerProfileService.ifEmployerExists(employerDto.getUserId())) {
            employerProfileService.createEmployer(employerDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Employer already exists");
    }

    @PostMapping("/edit_employer")
    public HttpStatus editEmployer(@RequestBody EmployerDto employerDto) {
        log.warn("Edited employer: {}", employerDto.getCompanyName());
        if (employerProfileService.ifEmployerExists(employerDto.getUserId())) {
            employerProfileService.editEmployer(employerDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Employer does not exist");
    }


    @GetMapping("/get_employer_by_id/{id}")
    public EmployerDto getEmployerById(@PathVariable Long id) {
        return employerProfileService.getEmployerById(id);
    }
    @GetMapping("/get_employer_by_company_name/{companyName}")
    public List<EmployerDto> getEmployerByCompanyName(@PathVariable String companyName) {
        return employerProfileService.getEmployerByCompanyName(companyName);
    }

}
