package com.example.jobsearch.service;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerProfileService {
    private final ResumeService resumeService;
    private final JobApplicationService jobApplicationService;
    private final EmployerService employerService;
    private final VacancyService vacancyService;
    private final ApplicantService applicantService;

    public List<ResumeDto> getAllResumesByVacancyId(long vacancyId) {
        List<Long> temp = jobApplicationService.getAllResumeIdsByVacancyId(vacancyId);
        return resumeService.getAllResumesByLongList(temp);
    }
    public void createVacancy(VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
    }

    public boolean ifEmployerExists(String userId) {
        return employerService.ifEmployerExists(userId);
    }

    public void createEmployer(EmployerDto employerDto) {
        employerService.createEmployer(employerDto);
    }

    public void editEmployer(EmployerDto employerDto) {
        employerService.editEmployer(employerDto);
    }

    public void editVacancy(VacancyDto vacancyDto) {
        vacancyService.editVacancy(vacancyDto);
    }

    public void deleteVacancy(VacancyDto vacancyDto) {
        vacancyService.deleteVacancy(vacancyDto);
    }

    public List<ResumeDto> getAllResumes() {
        return resumeService.getAllResumes();
    }

    public List<ResumeDto> getResumeByResumeTitle(String title) {
        return resumeService.getResumeByResumeTitle(title);
    }

    public ApplicantDto getApplicantById(Long applicantId) {
        return applicantService.getApplicantById(applicantId);
    }

    public List<ResumeDto> getResumesByCategoryName(String category) {
        return resumeService.getAllResumesByCategoryName(category);
    }

    public List<ResumeDto> getAllResumesByCategoryId(long id) {
        return resumeService.getAllResumesByCategoryId(id);
    }
}
