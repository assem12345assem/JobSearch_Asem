package com.example.jobsearch.service;

import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantProfileService {
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final JobApplicationService jobApplicationService;
    private final ApplicantService applicantService;
    private final EmployerService employerService;

    public String displayAge(ApplicantDto applicantDto) {
        LocalDate l = LocalDate.now();
        Period intervalPeriod = Period.between(applicantDto.getDateOfBirth(), l);
        return intervalPeriod.getYears() + " years old";
    }
    public List<ResumeDto> getMyResumes(ApplicantDto applicantDto) {
        return resumeService.getAllResumesByApplicantId(applicantDto.getId());
    }
    public List<VacancyDto> getAllMyAppliedVacancies(ApplicantDto applicantDto) {
        List<ResumeDto> allMyResumes = getMyResumes(applicantDto);
        List<Long> ids = new ArrayList<>();
        allMyResumes.forEach(e -> ids.add(e.getId()));
        List<Long> allMyVacancyApplications = jobApplicationService.getAllVacanciesByResumeList(ids);
        return vacancyService.getVacancyListByIdList(allMyVacancyApplications);
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

    public void createResume(ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
    }

    public void editResume(ResumeDto resumeDto) {
        resumeService.editResume(resumeDto);
    }

    public void deleteResume(ResumeDto resumeDto) {
        resumeService.deleteResume(resumeDto);
    }

    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    public List<VacancyDto> getAllVacanciesByCategory(String category) {
        return vacancyService.getAllVacanciesByCategory(category);
    }

    public void applyForVacancy(long vacancyId, long resumeId) {
        jobApplicationService.applyForVacancy(vacancyId, resumeId);
    }

    public EmployerDto getEmployerById(Long id) {
        return employerService.getEmployerById(id);
    }

    public List<EmployerDto> getEmployerByCompanyName(String companyName) {
        return employerService.getEmployerByCompanyName(companyName);
    }
}
