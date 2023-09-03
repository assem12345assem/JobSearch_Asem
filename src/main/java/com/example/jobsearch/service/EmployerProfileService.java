package com.example.jobsearch.service;

import com.example.jobsearch.dao.EmployerDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerProfileService {
    private final EmployerDao employerDao;

    public List<EmployerDto> getAllEmployers() {
        List<Employer> list = employerDao.getAllEmployers();
        return list.stream()
                .map(this::makeDtoFromEmployer)
                .toList();
    }
    public void saveEmployer(EmployerDto employerDto) throws Exception {
        if(!ifEmployerExists(employerDto.getUserId())) {
            Employer employer = makeEmployerFromDto(employerDto);
            employerDao.save(employer);
        } else {
            log.info("Employer save error: Employer already exists {}", employerDto.getUserId());
            throw new Exception("Employer already exists");
        }
    }
    public boolean ifEmployerExists(String userId) {
        var e = employerDao.getEmployerByUserId(userId);
        return e.isPresent();
    }
    public Optional<EmployerDto> getEmployerByUserId(String email) {
        var e = employerDao.getEmployerByUserId(email);
        if(e.isPresent()) return Optional.of(makeDtoFromEmployer(e.get()));
        else throw new NoSuchElementException("Employer profile does not exist");

    }
    public ResponseEntity<?> findEmployerByUserId(String email) {
        var maybeEmployer = employerDao.getEmployerByUserId(email);
        return handleEmployerQueries(maybeEmployer);
    }

    public ResponseEntity<?> getEmployerById(Long id) {
        var maybeEmployer = employerDao.getEmployerById(id);
        return handleEmployerQueries(maybeEmployer);
    }

    public ResponseEntity<?> createEmployer(EmployerDto employerDto) {
        if(!ifEmployerExists(employerDto.getUserId())) {
            Employer employer = makeEmployerFromDto(employerDto);
            employerDao.save(employer);
            return new ResponseEntity<>("Employer successfully created", HttpStatus.OK);
        } else {
            log.info("Employer save error: Employer already exists {}", employerDto.getUserId());
            return new ResponseEntity<>("Employer already exists", HttpStatus.OK);
        }

    }
    public void edit(EmployerDto e) {
        employerDao.editEmployer(makeEmployerFromDto(e));
    }


    public String getUserIdByEmployerId(Long employerId) {
        return employerDao.getUserIdByEmployerId(employerId);
    }

    public ResponseEntity<?> getEmployerByCompanyName(String companyName) {
        List<Employer> employers = employerDao.getEmployerByCompanyName(companyName);
        if(employers.isEmpty()) {
            log.info("getEmployerByCompanyName: company does not exist {}", companyName);
            return new ResponseEntity<>("company does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(employers.stream()
                    .map(this::makeDtoFromEmployer)
                    .toList(), HttpStatus.OK);
        }
    }
    private EmployerDto makeDtoFromEmployer(Employer employer) {
        EmployerDto e = new EmployerDto();
        e.setId(employer.getId());
        e.setUserId(employer.getUserId());
        e.setCompanyName(employer.getCompanyName());
        return e;
    }
    public Employer makeEmployerFromDto(EmployerDto employerDto) {
        Employer e = new Employer();
        e.setId(employerDto.getId());
        e.setUserId(employerDto.getUserId());
        e.setCompanyName(employerDto.getCompanyName());
        return e;
    }
    private ResponseEntity<?> handleEmployerQueries(Optional<Employer> maybeEmployer) {
        if(maybeEmployer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeDtoFromEmployer(maybeEmployer.get()), HttpStatus.OK);
    }

    public void editEmployer(Employer employer) {
        employerDao.editEmployer(employer);
    }
}
