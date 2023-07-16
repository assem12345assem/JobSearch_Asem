package com.example.jobsearch.service;

import com.example.jobsearch.dao.EmployerDao;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.model.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerService {
    private final EmployerDao employerDao;
    private final VacancyService vacancyService;
    public List<EmployerDto> getAllEmployers() {
        List<Employer> list = employerDao.getAllEmployers();
        return list.stream()
                .map(this::makeDtoFromEmployer)
                .toList();
    }
    private EmployerDto makeDtoFromEmployer (Employer e) {
        return EmployerDto.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .companyName(e.getCompanyName())
                .build();
    }
    public EmployerDto getEmployerByUserId(String email) {
        return makeDtoFromEmployer(employerDao.getEmployerByUserId(email));
    }
    public EmployerDto getEmployerById(Long id) {
        return makeDtoFromEmployer(employerDao.getEmployerById(id));
    }
    public boolean ifEmployerExists(String userEmail) {
        return employerDao.ifEmployerExists(userEmail);
    }
    public void createEmployer(EmployerDto employerDto) {
        Employer employer = createEmployerFromDto(employerDto);
        employerDao.createEmployer(employer);
    }
    private Employer createEmployerFromDto(EmployerDto employerDto) {
        return Employer.builder()
                .id(employerDto.getId())
                .userId(employerDto.getUserId())
                .companyName(employerDto.getCompanyName())
                .build();
    }
    public void editEmployer(EmployerDto employerDto) {
        Employer employer = createEmployerFromDto(employerDto);
        employerDao.editEmployer(employer);
    }

    public List<EmployerDto> getEmployerByCompanyName(String companyName) {
        List<Employer> employers = employerDao.getEmployerByCompanyName(companyName);
        return employers.stream()
                .map(this::makeDtoFromEmployer)
                .toList();
    }
}
