package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.model.Applicant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantProfileService {
    private final ApplicantDao applicantDao;

    public String displayAge(Long applicantId) {
        ApplicantDto applicantDto = getApplicantById(applicantId);
        LocalDate l = LocalDate.now();
        Period intervalPeriod = Period.between(applicantDto.getDateOfBirth(), l);
        return intervalPeriod.getYears() + " years old";
    }

    public boolean ifApplicantExists(String userId) {
        return applicantDao.ifApplicantExists(userId);
    }

    public void createApplicant(ApplicantDto applicantDto) {
        log.warn("Created new applicant: {}", applicantDto.getLastName());
        applicantDao.save(buildApplicantFromDto(applicantDto));

    }

    public void editApplicant(ApplicantDto applicantDto) {
        log.warn("Edited applicant: {}", applicantDto.getLastName());
        applicantDao.editApplicant(buildApplicantFromDto(applicantDto));
    }

    public void deleteApplicant(ApplicantDto e) {
        applicantDao.delete(e.getId());
    }

    public ApplicantDto getApplicantById(Long applicantId) {
        return makeDtoFromApplicant(applicantDao.getApplicantById(applicantId));
    }

    public ApplicantDto getApplicantByUserId(String userId) {
        return makeDtoFromApplicant(applicantDao.getApplicantByUserId(userId));
    }

    public ApplicantDto getApplicantByFirstName(String firstName) {
        return makeDtoFromApplicant(applicantDao.getApplicantByFirstName(firstName));
    }

    public ApplicantDto getApplicantByLastName(String lastName) {
        return makeDtoFromApplicant(applicantDao.getApplicantByLastName(lastName));
    }

    public List<ApplicantDto> getAllApplicants() {
        List<Applicant> list = applicantDao.getAllApplicants();
        return list.stream()
                .map(this::makeDtoFromApplicant)
                .toList();
    }

    private Applicant buildApplicantFromDto(ApplicantDto applicantDto) {
        Applicant a = new Applicant();

        a.setId(applicantDto.getId());
        a.setUserId(applicantDto.getUserId());
        a.setFirstName((applicantDto.getFirstName()));
        a.setLastName(applicantDto.getLastName());
        a.setDateOfBirth(applicantDto.getDateOfBirth());
        return a;
    }

    private ApplicantDto makeDtoFromApplicant(Applicant applicant) {
        ApplicantDto a = new ApplicantDto();

        a.setId(applicant.getId());
        a.setUserId(applicant.getUserId());
        a.setFirstName((applicant.getFirstName()));
        a.setLastName(applicant.getLastName());
        a.setDateOfBirth(applicant.getDateOfBirth());
        return a;
    }
}
