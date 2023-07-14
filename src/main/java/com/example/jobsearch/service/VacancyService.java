package com.example.jobsearch.service;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyDao vacancyDao;
    private final EmployerService employerService;
    private final CategoryService categoryService;
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> list = vacancyDao.getAllVacancies();
        return list.stream()
                .map(this::makeDtoFromVacancy)
                .toList();
    }
    private VacancyDto makeDtoFromVacancy(Vacancy v) {
        return VacancyDto.builder()
                .id(v.getId())
                .employer(employerService.getEmployerByUserId(v.getEmployerId()))
                .vacancyName(v.getVacancyName())
                .category(categoryService.getCategoryById(v.getCategoryId()))
                .salary(v.getSalary())
                .description(v.getDescription())
                .requiredExperienceMin(v.getRequiredExperienceMin())
                .requiredExperienceMax(v.getRequiredExperienceMax())
                .isActive(v.isActive())
                .isPublished(v.isPublished())
                .publishedDateTime(v.getPublishedDateTime())
                .build();
    }
    public List<VacancyDto> getVacanciesByUserId (String email) {
        List<Vacancy> list = vacancyDao.getAllEmployerVacanciesByUserId(email);
        return list.stream()
                .map(this::makeDtoFromVacancy)
                .toList();
    }
    public List<VacancyDto> getAllVacanciesByCategory(Long categoryId) {
        List<Vacancy> list = vacancyDao.getAllVacanciesByCategory(categoryId);
        return list.stream()
                .map(this::makeDtoFromVacancy)
                .toList();
    }

}
