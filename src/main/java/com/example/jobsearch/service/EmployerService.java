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
}
