package com.example.jobsearch.service;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.enums.Category;
import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyDao vacancyDao;
    public List<Vacancy> getAllVacancies() {
        return vacancyDao.getAllVacancies();
    }
    public List<Vacancy> getVacanciesByUserId(int userId) {
        return vacancyDao.getAllEmployerVacanciesByUserId(userId);
    }
    public List<Vacancy> getAllVacanciesByCategory(Category category) {
        return vacancyDao.getAllVacanciesByCategory(category);
    }

}
