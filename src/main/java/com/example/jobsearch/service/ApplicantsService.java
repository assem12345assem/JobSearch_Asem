package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantsDao;
import com.example.jobsearch.model.Applicants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantsService {
    private final ApplicantsDao applicantsDao;
    public List<Applicants> getAllApplicants() {
        return applicantsDao.getAllApplicants();
    }
    public List<Applicants> getAllApplicantsByVacancyId(int vacancyId) {
        return applicantsDao.getAllApplicantsByVacancyId(vacancyId);
    }
    public List<Applicants> getAllVacanciesByApplicantId(int applicantUserId) {
        return applicantsDao.getAllVacanciesByApplicantId(applicantUserId);
    }


}
