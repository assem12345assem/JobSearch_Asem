package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.service.ApplicantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantProfileController {
    private final ApplicantProfileService applicantProfileService;
    @GetMapping
    public List<ApplicantDto> getAllApplicants() {
        return applicantProfileService.getAllApplicants();
    }
    @PostMapping("/create_applicant")
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantDto applicantDto) {
        return applicantProfileService.createApplicant(applicantDto);
    }
    @PostMapping("/edit_applicant")
    public ResponseEntity<?> editApplicant(@RequestBody ApplicantDto applicantDto, Authentication auth) {
       return applicantProfileService.editApplicant(applicantDto, auth);
    }
    @GetMapping("/{applicantId}")
    public ResponseEntity<?> findApplicantById(@PathVariable Long applicantId) {
        return applicantProfileService.findApplicantById(applicantId);
    }
    @GetMapping("/show_age/{applicantId}")
    public String showAge(@PathVariable Long applicantId) {
        return applicantProfileService.displayAge(applicantId);
    }
    @GetMapping("/view_user/{userId}")
    public ApplicantDto getApplicantByUserId (@PathVariable String userId) {
        return applicantProfileService.getApplicantByUserId(userId);
    }
    @GetMapping("/get_applicant_by_first_name/{firstName}")
    public ApplicantDto getApplicantByFirstName (@PathVariable String firstName) {
        return applicantProfileService.getApplicantByFirstName(firstName);
    }
    @GetMapping("/get_applicant_by_last_name/{lastName}")
    public ApplicantDto getApplicantByLastName (@PathVariable String lastName) {
        return applicantProfileService.getApplicantByLastName(lastName);
    }


}
