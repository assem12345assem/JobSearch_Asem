package com.example.jobsearch.service;

import com.example.jobsearch.dto.ApplicantDto;
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


}
