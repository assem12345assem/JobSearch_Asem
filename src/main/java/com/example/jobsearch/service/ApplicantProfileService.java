package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.model.Applicant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void saveApplicant(ApplicantDto applicantDto) throws Exception {
        if (!ifApplicantExists(applicantDto.getUserId())) {
            applicantDao.save(makeApplicantFromDto(applicantDto));
        } else {
            log.info("Tried to create an applicant that exists: {}", applicantDto.getLastName());
            throw new Exception("Applicant already exists");
        }
    }

    public void editApplicant(ApplicantDto applicantDto) {
        applicantDao.editApplicant(makeApplicantFromDto(applicantDto));
    }

    public boolean ifApplicantExists(String userId) {
        var a = applicantDao.getApplicantByUserId(userId);
        return a.isPresent();
    }

    public Optional<ApplicantDto> getApplicantByUserId(String userId) {
        var a = applicantDao.getApplicantByUserId(userId);
        if (a.isPresent()) return Optional.of(makeDtoFromApplicant(a.get()));
        else throw new NoSuchElementException("Applicant profile not found");
    }

    public ResponseEntity<?> createApplicant(ApplicantDto applicantDto) {
        if (!ifApplicantExists(applicantDto.getUserId())) {
            applicantDao.save(makeApplicantFromDto(applicantDto));
            return new ResponseEntity<>("Applicant created", HttpStatus.OK);
        } else {
            log.info("Tried to create an applicant that exists: {}", applicantDto.getLastName());
            return new ResponseEntity<>("Applicant already exists", HttpStatus.OK);
        }
    }


    public ResponseEntity<?> findApplicantById(Long applicantId) {
        var maybeApplicant = applicantDao.findApplicantById(applicantId);
        return handleApplicantQueries(maybeApplicant);
    }

    public ApplicantDto getApplicantById(long id) {
        return makeDtoFromApplicant(applicantDao.getApplicantById(id));
    }

    public ResponseEntity<?> findApplicantByUserId(String userId) {
        var maybeApplicant = applicantDao.getApplicantByUserId(userId);
        return handleApplicantQueries(maybeApplicant);
    }

    public ResponseEntity<?> getApplicantByFirstName(String firstName) {
        var maybeApplicant = applicantDao.getApplicantByFirstName(firstName);
        return handleApplicantQueries(maybeApplicant);
    }

    public ResponseEntity<?> getApplicantByLastName(String lastName) {
        var maybeApplicant = applicantDao.getApplicantByLastName(lastName);
        return handleApplicantQueries(maybeApplicant);
    }

    public List<ApplicantDto> getAllApplicants() {
        List<Applicant> list = applicantDao.getAllApplicants();
        return list.stream()
                .map(this::makeDtoFromApplicant)
                .toList();
    }

    private Applicant makeApplicantFromDto(ApplicantDto applicantDto) {
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

    private ResponseEntity<?> handleApplicantQueries(Optional<Applicant> maybeApplicant) {
        if (maybeApplicant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeDtoFromApplicant(maybeApplicant.get()), HttpStatus.OK);
    }


}
