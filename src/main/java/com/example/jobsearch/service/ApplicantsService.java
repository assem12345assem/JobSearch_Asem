package com.example.jobsearch.service;

import com.example.jobsearch.dao.VacancyApplicantsDao;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.model.VacancyApplicants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantsService {
    private final VacancyApplicantsDao applicantsDao;
    public List<VacancyApplicants> getAllApplicants() {
        return applicantsDao.getAllApplicants();
    }
    public List<VacancyApplicants> getAllApplicantsByVacancyId(int vacancyId) {
        return applicantsDao.getAllApplicantsByVacancyId(vacancyId);
    }

}
