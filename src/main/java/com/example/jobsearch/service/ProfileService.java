package com.example.jobsearch.service;

import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ApplicantProfileService applicantService;
    private final EmployerProfileService employerService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;
    public Object getUserProfile(String userId) {
        User u = userService.getUserByEmail(userId);
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            return applicantService.getApplicantByUserId(u.getId()).get();
        } else if (u.getUserType().equalsIgnoreCase("employer")) {
            return employerService.getEmployerByUserId(u.getId()).get();
        } else throw new NoSuchElementException("User type is not found");
    }

    public List<?> getProfileContent(String userId) throws Exception {
        User u = userService.getUserByEmail(userId);
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            return resumeService.getAllResumesByApplicantId(applicantService.getApplicantByUserId(u.getId()).get().getId());
        } else if (u.getUserType().equalsIgnoreCase("employer")) {
            return vacancyService.getAllVacanciesByEmployerId(employerService.getEmployerByUserId(u.getId()).get().getId());
        } else throw new NoSuchElementException("User type is not found");
    }
}
