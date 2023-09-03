package com.example.jobsearch.service;

import com.example.jobsearch.dao.JobApplicationDao;
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
import java.util.Optional;

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

    public ResponseEntity<?> apply(long vacancyId, long resumeId, Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = userService.getUserFromAuth(u.toString());
        ResumeDto r = resumeService.getResumeById(resumeId);
        if(r.getAuthorEmail().equalsIgnoreCase(user.get().getId())) {
            var jobApp = jobApplicationDao.getVacancyByIdAndResumeId(vacancyId, resumeId);
            if(jobApp == null) {
                return new ResponseEntity<>(jobApplicationDao.save(JobApplication.builder()
                        .vacancyId(vacancyId)
                        .resumeId(resumeId)
                        .dateTime(LocalDateTime.now())
                        .build()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User already applied for this vacancy", HttpStatus.OK);
            }
        } else {
            log.warn("Job Application - Resume does not belong to the user: {}", user.get().getId());
            return new ResponseEntity<>("Authentication id and resume's owner ids do not match", HttpStatus.BAD_REQUEST);
        }

    }
}
