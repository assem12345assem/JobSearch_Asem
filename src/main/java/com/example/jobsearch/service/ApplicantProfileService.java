package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.model.Applicant;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantProfileService {
    private final ApplicantDao applicantDao;
    private final UserService userService;

    public String displayAge(Long applicantId) {
        ApplicantDto applicantDto = getApplicantById(applicantId);
        LocalDate l = LocalDate.now();
        Period intervalPeriod = Period.between(applicantDto.getDateOfBirth(), l);
        return intervalPeriod.getYears() + " years old";
    }

    public boolean ifApplicantExists(String userId) {
        return applicantDao.ifApplicantExists(userId);
    }

    public ResponseEntity<?> createApplicant(ApplicantDto applicantDto) {
        if (!ifApplicantExists(applicantDto.getUserId())) {
            applicantDao.save(buildApplicantFromDto(applicantDto));
            return new ResponseEntity<>("Applicant created", HttpStatus.OK);
        } else {
            log.info("Tried to create an applicant that exists: {}", applicantDto.getLastName());
            return new ResponseEntity<>("Applicant already exists", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editApplicant(ApplicantDto applicantDto, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if (user.getId().equalsIgnoreCase(applicantDto.getUserId())) {
            if (ifApplicantExists(applicantDto.getUserId())) {
                applicantDao.editApplicant(buildApplicantFromDto(applicantDto));
                return new ResponseEntity<>("Applicant is edited", HttpStatus.OK);
            } else {
                log.warn("Tried to edit someone else's profile: {}", user.getId());
                return new ResponseEntity<>("Tried to edit other user's profile", HttpStatus.BAD_REQUEST);
            }

        } else {
            log.warn("Tried to edit applicant that does not exist: {}", applicantDto.getLastName());
            return new ResponseEntity<>("Applicant does not exist", HttpStatus.OK);
        }

    }

    public void deleteApplicant(ApplicantDto e) {
        applicantDao.delete(e.getId());
    }

    public ResponseEntity<?> findApplicantById(Long applicantId) {
        var maybeApplicant = applicantDao.findApplicantById(applicantId);
        if (maybeApplicant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(
                makeDtoFromApplicant(maybeApplicant.get()),
                HttpStatus.OK
        );
    }
    public ApplicantDto getApplicantById(long id) {
        return makeDtoFromApplicant(applicantDao.getApplicantById(id));
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
