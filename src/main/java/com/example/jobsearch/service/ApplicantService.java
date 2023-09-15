package com.example.jobsearch.service;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.entity.Applicant;
import com.example.jobsearch.entity.User;
import com.example.jobsearch.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    public Applicant getApplicantByUserEmail (String email) {
        return applicantRepository.findByUserEmail(email).orElseThrow(() -> {
            log.info("Applicant is not found by user email: {}", email);
            return new NoSuchElementException("Applicant not found");

        });
    }
    public Applicant getApplicantById (Long id) {
        return applicantRepository.findById(id).orElseThrow(() -> {
            log.info("Applicant is not found by applicant id: {}", id);
            return new NoSuchElementException("Applicant not found");

        });
    }
    public ApplicantDto getApplicantDtoById(Long id) {
        Applicant a = getApplicantById(id);
        return makeDtoFromApplicant(a);
    }
    public ApplicantDto getApplicantDtoByUserEmail (String email) {
        Applicant a = getApplicantByUserEmail(email);
        return makeDtoFromApplicant(a);
    }
    public void save(User user) {
        applicantRepository.save(Applicant.builder()
                .user(user)
                .build());
    }
    public void saveApplicant(Applicant a) {
        applicantRepository.save(a);
    }
    public ApplicantDto makeDtoFromApplicant(Applicant a) {
        return ApplicantDto.builder()
                .id(a.getId())
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
    }
}
