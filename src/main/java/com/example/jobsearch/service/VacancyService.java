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
                .employerId(v.getEmployerId())
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
    private Vacancy makeVacancyFromDto(VacancyDto v) {
        return Vacancy.builder()
                .id(v.getId())
                .employerId(v.getId())
                .vacancyName(v.getVacancyName())
                .categoryId(v.getCategory().getId())
                .salary(v.getSalary())
                .description(v.getDescription())
                .requiredExperienceMin(v.getRequiredExperienceMin())
                .requiredExperienceMax(v.getRequiredExperienceMax())
                .isActive(v.isActive())
                .isPublished(v.isPublished())
                .publishedDateTime(v.getPublishedDateTime())
                .build();
    }

    public List<VacancyDto> getAllVacanciesByCategory(Long categoryId) {
        List<Vacancy> list = vacancyDao.getAllVacanciesByCategory(categoryId);
        return list.stream()
                .map(this::makeDtoFromVacancy)
                .toList();
    }
    public List<VacancyDto> getAllVacanciesByCategory(String category) {
        Long id = categoryService.getIdByCategory(category);
        return getAllVacanciesByCategory(id);
    }
    public VacancyDto getVacancyById(Long id) {
        return makeDtoFromVacancy(vacancyDao.getVacancyById(id));
    }
    public List<VacancyDto> getVacancyListByIdList(List<Long> id) {
        List<Vacancy> list = vacancyDao.getVacancyListByIdList(id);
        return list.stream()
                .map(this::makeDtoFromVacancy)
                .toList();
    }
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = makeVacancyFromDto(vacancyDto);
        vacancyDao.createVacancy(vacancy);
    }

    public void editVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = makeVacancyFromDto(vacancyDto);
        vacancyDao.editVacancy(vacancy);
    }

    public void deleteVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = makeVacancyFromDto(vacancyDto);
        vacancyDao.deleteVacancy(vacancy);
    }
}
