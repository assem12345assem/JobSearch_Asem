package com.example.jobsearch.service;

import com.example.jobsearch.dao.EmployerDao;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.model.Employer;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private EmployerDto makeDtoFromEmployer(Employer employer) {
        EmployerDto e = new EmployerDto();
        e.setId(employer.getId());
        e.setUserId(employer.getUserId());
        e.setCompanyName(employer.getCompanyName());
        return e;
    }

    public EmployerDto getEmployerByUserId(String email) {
        return makeDtoFromEmployer(employerDao.getEmployerByUserId(email));
    }

    public EmployerDto getEmployerById(Long id) {
        return makeDtoFromEmployer(employerDao.getEmployerById(id));
    }

    public boolean ifEmployerExists(String userId) {
        return employerDao.ifEmployerExists(userId);
    }

    public void createEmployer(EmployerDto employerDto) {
        log.warn("Created new employer: {}", employerDto.getCompanyName());
        Employer employer = createEmployerFromDto(employerDto);
        employerDao.save(employer);
    }

    private Employer createEmployerFromDto(EmployerDto employerDto) {
        Employer e = new Employer();
        e.setId(employerDto.getId());
        e.setUserId(employerDto.getUserId());
        e.setCompanyName(employerDto.getCompanyName());
        return e;
    }

    public void editEmployer(EmployerDto employerDto, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(user.getId().equalsIgnoreCase(employerDto.getUserId())) {
            Employer employer = createEmployerFromDto(employerDto);
            employerDao.editEmployer(employer);
        }
    }
    public String getUserIdByEmployerId(Long employerId) {
        return employerDao.getUserIdByEmployerId(employerId);
    }

    public List<EmployerDto> getEmployerByCompanyName(String companyName) {
        List<Employer> employers = employerDao.getEmployerByCompanyName(companyName);
        return employers.stream()
                .map(this::makeDtoFromEmployer)
                .toList();
    }


}
