package com.example.jobsearch.service;

import com.example.jobsearch.dao.JobApplicationDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.JobApplicationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.model.JobApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationDao jobApplicationDao;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final ApplicantProfileService service;

    public List<JobApplicationDto> getAllJobApplications() {
        List<JobApplication> jobApplications = jobApplicationDao.getAllJobApplications();

        return jobApplications.stream()
                .map(this::makeDtoFromJobApplication)
                .toList();
    }

    private JobApplicationDto makeDtoFromJobApplication(JobApplication a) {
        JobApplicationDto j = new JobApplicationDto();
        j.setId(a.getId());
        j.setVacancyDto(vacancyService.getVacancyById(a.getVacancyId()));
        j.setResumeDto(resumeService.getResumeById(a.getResumeId()));
        return j;
    }

    private List<Long> getAllVacanciesByResumeList(List<Long> resumeId) {
        return jobApplicationDao.getAllVacanciesByResumeList(resumeId);
    }

    public List<Long> getAllResumeIdsByVacancyId(long vacancyId) {
        return jobApplicationDao.getAllResumeIdsByVacancyId(vacancyId);
    }

    public List<ResumeDto> getResumesByVacancyId(long vacancyId) {
        List<Long> ids = getAllResumeIdsByVacancyId(vacancyId);
        return resumeService.getAllResumesByLongList(ids);
    }

//    public void applyForVacancy(long vacancyId, long resumeId) {
//        log.warn("Vacancy application: {}", vacancyId);
//        Vacancy v = getVacancyByIdAndResumeId(vacancyId, resumeId);
//        jobApplicationDao.save(v);
//    }

    private VacancyDto getVacancyByIdAndResumeId(long vacancyId, long resumeId) {
        JobApplication j = jobApplicationDao.getVacancyByIdAndResumeId(vacancyId, resumeId);
    return vacancyService.getVacancyById(j.getVacancyId());
    }

    public List<ResumeDto> getMyResumes(ApplicantDto applicantDto) {
        return resumeService.getAllResumesByApplicantId(applicantDto.getId());
    }

    public List<VacancyDto> getAllAppliedVacanciesByApplicantId(long applicantId) {
        ApplicantDto applicantDto = service.getApplicantById(applicantId);
        List<ResumeDto> allMyResumes = getMyResumes(applicantDto);
        List<Long> ids = new ArrayList<>();
        allMyResumes.forEach(e -> ids.add(e.getId()));
        List<Long> allMyVacancyApplications = getAllVacanciesByResumeList(ids);
        return vacancyService.getVacancyListByIdList(allMyVacancyApplications);
    }
}
