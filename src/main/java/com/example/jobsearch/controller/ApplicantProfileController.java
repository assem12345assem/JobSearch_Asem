package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.service.ApplicantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public HttpStatus createApplicant(@RequestBody ApplicantDto applicantDto) {
        if (!applicantProfileService.ifApplicantExists(applicantDto.getUserId())) {
            applicantProfileService.createApplicant(applicantDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Applicant already exists");
    }
    @PostMapping("/edit_applicant")
    public HttpStatus editApplicant(@RequestBody ApplicantDto applicantDto) {
        if (applicantProfileService.ifApplicantExists(applicantDto.getUserId())) {
            applicantProfileService.editApplicant(applicantDto);
            return HttpStatus.OK;
        }
        return HttpStatus.valueOf("Applicant does not exist");
    }


    @GetMapping("/get_applicant_by_id/{applicantId}")
    public ApplicantDto getApplicantById(@PathVariable Long applicantId) {
        return applicantProfileService.getApplicantById(applicantId);
    }
    @GetMapping("/show_age/{applicantId}")
    public String showAge(@PathVariable Long applicantId) {
        return applicantProfileService.displayAge(applicantId);
    }
    @GetMapping("/get_applicant_by_user_id/{userId}")
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
