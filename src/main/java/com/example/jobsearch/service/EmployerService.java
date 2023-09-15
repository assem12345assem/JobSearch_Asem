package com.example.jobsearch.service;

import com.example.jobsearch.entity.Employer;
import com.example.jobsearch.entity.User;
import com.example.jobsearch.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerService {
    private final EmployerRepository employerRepository;

    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id).orElseThrow(() -> {
            log.info("Employer not found by id: {}", id);
            return new NoSuchElementException("Employer does  not exist");
        });
    }

    public Employer getEmployerByUserEmail(String email) {
        return employerRepository.findByUserEmail(email).orElseThrow(() -> {
            log.info("Employer not found by user email: {}", email);
            return new NoSuchElementException("Employer does  not exist");
        });
    }

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public void save(User user) {
         employerRepository.save(Employer.builder()
                .user(user)
                .companyName(user.getUserName())
                .build());
    }

    public void saveEmployer(Employer e) {
        employerRepository.save(e);
    }
}
