package com.example.jobsearch.service;

import com.example.jobsearch.dto.ApplicantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantProfileService {
    private final ApplicantService applicantService;

    public String displayAge(Long applicantId) {
        ApplicantDto applicantDto = getApplicantById(applicantId);
        LocalDate l = LocalDate.now();
        Period intervalPeriod = Period.between(applicantDto.getDateOfBirth(), l);
        return intervalPeriod.getYears() + " years old";
    }

    public boolean ifApplicantExists(String userId) {
        return applicantService.ifApplicantExists(userId);
    }

    public void createApplicant(ApplicantDto applicantDto) {
        applicantService.createApplicant(applicantDto);
    }

    public void editApplicant(ApplicantDto applicantDto) {
        applicantService.editApplicant(applicantDto);
    }

    public ApplicantDto getApplicantById(Long applicantId) {
        return applicantService.getApplicantById(applicantId);
    }


    public ApplicantDto getApplicantByUserId(String userId) {
        return applicantService.getApplicantByUserId(userId);
    }

    public ApplicantDto getApplicantByFirstName(String firstName) {
        return applicantService.getApplicantByFirstName(firstName);
    }

    public ApplicantDto getApplicantByLastName(String lastName) {
        return applicantService.getApplicantByLastName(lastName);
    }

    public List<ApplicantDto> getAllApplicants() {
        return applicantService.getAllApplicants();
    }
}
