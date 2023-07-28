package com.example.jobsearch.service;

import com.example.jobsearch.dao.EmployerDao;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.model.Employer;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerProfileService {
    private final EmployerDao employerDao;
    private final UserService userService;

    public List<EmployerDto> getAllEmployers() {
        List<Employer> list = employerDao.getAllEmployers();
        return list.stream()
                .map(this::makeDtoFromEmployer)
                .toList();
    }

    public ResponseEntity<?> getEmployerByUserId(String email) {
        var maybeEmployer = employerDao.getEmployerByUserId(email);
        return handleEmployerQueries(maybeEmployer);
    }

    public ResponseEntity<?> getEmployerById(Long id) {
        var maybeEmployer = employerDao.getEmployerById(id);
        return handleEmployerQueries(maybeEmployer);
    }

    private boolean ifEmployerExists(String userId) {
        return employerDao.ifEmployerExists(userId);
    }

    public ResponseEntity<?> createEmployer(EmployerDto employerDto) {
        if(!ifEmployerExists(employerDto.getUserId())) {
            Employer employer = createEmployerFromDto(employerDto);
            employerDao.save(employer);
            return new ResponseEntity<>("Employer successfully created", HttpStatus.OK);
        } else {
            log.info("Employer save error: Employer already exists {}", employerDto.getUserId());
            return new ResponseEntity<>("Employer already exists", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editEmployer(EmployerDto employerDto, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());

        if(ifEmployerExists(employerDto.getUserId())) {
            if(user.getId().equalsIgnoreCase(employerDto.getUserId())) {
                Employer employer = createEmployerFromDto(employerDto);
                employerDao.editEmployer(employer);
                return new ResponseEntity<>("Employer was edited successfully", HttpStatus.OK);
            } else {
                log.warn("User tried to edit another employer's profile: {} {}", user.getId(), employerDto.getUserId());
                return new ResponseEntity<>("Error: attempt to edit other user's profile", HttpStatus.NOT_FOUND);
            }
        } else {
            log.warn("EDIT EMPLOYER Error: Employer does not exist {}", employerDto.getUserId());
            return new ResponseEntity<>("Employer does not exist", HttpStatus.NOT_FOUND);
        }
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
    private Employer createEmployerFromDto(EmployerDto employerDto) {
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
}
