package com.example.jobsearch.service;

import com.example.jobsearch.dao.JobApplicationDao;
import com.example.jobsearch.dto.JobApplicationDto;
import com.example.jobsearch.model.JobApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationDao jobApplicationDao;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    public List<JobApplicationDto> getAllJobApplications() {
        List<JobApplication> jobApplications = jobApplicationDao.getAllJobApplications();

        return jobApplications.stream()
                .map(this::makeDtoFromJobApplication)
                .toList();
    }
    private JobApplicationDto makeDtoFromJobApplication(JobApplication a) {
        return JobApplicationDto.builder()
                .id(a.getId())
                .vacancyDto(vacancyService.getVacancyById(a.getVacancyId()))
                .resumeDto(resumeService.getResumeById(a.getResumeId()))
                .build();
    }

    public List<Long> getAllVacanciesByResumeId(long resumeId) {
        return jobApplicationDao.getAllVacanciesByResumeId(resumeId);
    }
    public List<Long> getAllVacanciesByResumeList(List<Long> resumeId) {
        return jobApplicationDao.getAllVacanciesByResumeList(resumeId);
    }
    public List<Long> getAllResumeIdsByVacancyId(long vacancyId) {
        return jobApplicationDao.getAllResumeIdsByVacancyId(vacancyId);
    }


    public void applyForVacancy(long vacancyId, long resumeId) {
        jobApplicationDao.applyForVacancy(vacancyId, resumeId);
    }
}
