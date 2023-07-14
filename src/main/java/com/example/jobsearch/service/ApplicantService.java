package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.model.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantDao applicantDao;
    public List<ApplicantDto> getAllApplicants() {
        List<Applicant> list = applicantDao.getAllApplicants();
        return list.stream()
                .map(this::makeDtoFromApplicant)
                .toList();
    }
    private ApplicantDto makeDtoFromApplicant(Applicant applicant) {
        return ApplicantDto.builder()
                .id(applicant.getId())
                .userId(applicant.getUserId())
                .firstName(applicant.getFirstName())
                .lastName(applicant.getLastName())
                .dateOfBirth(applicant.getDateOfBirth())
                .build();
    }
    public ApplicantDto getApplicantById (long id) {
        return makeDtoFromApplicant(applicantDao.getApplicantById(id));
    }
    public ApplicantDto getApplicantByUserId (String userId) {
        return makeDtoFromApplicant(applicantDao.getApplicantByUserId(userId));
    }
    public ApplicantDto getApplicantByFirstName (String firstName) {
        return makeDtoFromApplicant(applicantDao.getApplicantByFirstName(firstName));
    }
    public ApplicantDto getApplicantByLastName (String lastName) {
        return makeDtoFromApplicant(applicantDao.getApplicantByLastName(lastName));
    }

    public void createApplicant(Applicant e) {
        applicantDao.createApplicant(e);
    }
    public void deleteApplicant(Applicant e) {
        applicantDao.deleteApplicant(e);
    }
    public void editApplicant(Applicant e) {
        applicantDao.editApplicant(e);
    }


}
