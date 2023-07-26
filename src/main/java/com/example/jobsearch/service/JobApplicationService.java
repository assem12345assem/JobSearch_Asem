package com.example.jobsearch.service;

import com.example.jobsearch.dao.JobApplicationDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.JobApplicationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.model.JobApplication;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final UserService userService;

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

    private VacancyDto getVacancyByIdAndResumeId(long vacancyId, long resumeId) {
        JobApplication j = jobApplicationDao.getVacancyByIdAndResumeId(vacancyId, resumeId);
    return vacancyService.getVacancyById(j.getVacancyId());
    }

    public List<ResumeDto> getMyResumes(ApplicantDto applicantDto) {
        return resumeService.getAllResumesByApplicantId(applicantDto.getId());
    }

    public ResponseEntity<?> getAllAppliedVacanciesByApplicantId(long applicantId) {
        ApplicantDto applicantDto = service.getApplicantById(applicantId);
        List<ResumeDto> allMyResumes = getMyResumes(applicantDto);
        List<Long> ids = new ArrayList<>();
        allMyResumes.forEach(e -> ids.add(e.getId()));
        List<Long> allMyVacancyApplications = getAllVacanciesByResumeList(ids);
        List<VacancyDto> list = vacancyService.getVacancyListByIdList(allMyVacancyApplications);
        if(list.isEmpty()) {
            return new ResponseEntity<>("Applicant ID does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    public void apply(long vacancyId, long resumeId, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        ResumeDto r = resumeService.getResumeById(resumeId);
        if(r.getApplicantDto().getUserId().equalsIgnoreCase(user.getId())) {
            jobApplicationDao.save(JobApplication.builder()
                            .vacancyId(vacancyId)
                            .resumeId(resumeId)
                            .dateTime(LocalDateTime.now())
                    .build());

        }

    }
}
