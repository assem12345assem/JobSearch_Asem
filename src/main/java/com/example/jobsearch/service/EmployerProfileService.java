package com.example.jobsearch.service;

import com.example.jobsearch.dto.EmployerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerProfileService {
    private final EmployerService employerService;





    public boolean ifEmployerExists(String userId) {
        return employerService.ifEmployerExists(userId);
    }

    public void createEmployer(EmployerDto employerDto) {
        employerService.createEmployer(employerDto);
    }

    public void editEmployer(EmployerDto employerDto) {
        employerService.editEmployer(employerDto);
    }


    public EmployerDto getEmployerById(Long id) {
        return employerService.getEmployerById(id);
    }

    public List<EmployerDto> getEmployerByCompanyName(String companyName) {
        return employerService.getEmployerByCompanyName(companyName);
    }

    public List<EmployerDto> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    public EmployerDto getEmployerByUserId(String email) {
        return employerService.getEmployerByUserId(email);
    }
}
