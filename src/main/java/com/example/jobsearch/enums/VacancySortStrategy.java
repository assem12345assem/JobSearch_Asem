package com.example.jobsearch.enums;

import com.example.jobsearch.model.Vacancy;

import java.util.Comparator;
import java.util.List;

public enum VacancySortStrategy {
    BY_DATE ("by_date") {
        @Override
        public List<Vacancy> sortingVacancies(List<Vacancy> vacancies) {
        vacancies.sort(Comparator.comparing(Vacancy::getPublishedDateTime));
            return vacancies;
        }
    },
    BY_CATEGORY ("by_category") {
        @Override
        public List<Vacancy> sortingVacancies(List<Vacancy> vacancies) {
            vacancies.sort(Comparator.comparing(Vacancy::getCategory));
            return vacancies;
        }
    },
    BY_SALARY ("by_salary") {
        @Override
        public List<Vacancy> sortingVacancies(List<Vacancy> vacancies) {
            vacancies.sort(Comparator.comparing(Vacancy::getSalary));
            return vacancies;
        }
    },
    BY_NAME ("by_name") {
        @Override
        public List<Vacancy> sortingVacancies(List<Vacancy> vacancies) {
            vacancies.sort(Comparator.comparing(Vacancy::getVacancyName));
            return vacancies;
        }
    };
    private final String value;

    VacancySortStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public static VacancySortStrategy fromString(String sortCriteria) throws Exception {
        for (var e : VacancySortStrategy.values()) {
            if (e.value.equalsIgnoreCase(sortCriteria)) {
                return e;
            }
        }
        throw new Exception("Sorted criteria not found");
    }

    public abstract List<Vacancy> sortingVacancies(List<Vacancy> vacancies);
}
