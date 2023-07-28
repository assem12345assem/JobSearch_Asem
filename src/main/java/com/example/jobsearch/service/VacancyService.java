package com.example.jobsearch.service;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.enums.VacancySortStrategy;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyDao vacancyDao;
    private final EmployerProfileService employerService;
    private final UserService userService;

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
                .category(v.getCategory())
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
                .employerId(v.getEmployerId())
                .vacancyName(v.getVacancyName())
                .category(v.getCategory())
                .salary(v.getSalary())
                .description(v.getDescription())
                .requiredExperienceMin(v.getRequiredExperienceMin())
                .requiredExperienceMax(v.getRequiredExperienceMax())
                .isActive(v.isActive())
                .isPublished(v.isPublished())
                .publishedDateTime(v.getPublishedDateTime())
                .build();

    }

    public ResponseEntity<?> getAllVacanciesByCategory(String category) {
        List<Vacancy> list = vacancyDao.getAllVacanciesByCategory(category);
        if(list.isEmpty()) {
            return new ResponseEntity<>("There are no vacancies for given category", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list.stream()
                    .map(this::makeDtoFromVacancy)
                    .toList(), HttpStatus.OK);
        }
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

    public void createVacancy(Long employerId, String vacancyName, String category, int salary, String description,
                              int minReqExp, int maxReqExp, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(employerService.getUserIdByEmployerId(employerId).equalsIgnoreCase(user.getId())) {
            vacancyDao.save(Vacancy.builder()
                            .employerId(employerId)
                            .vacancyName(vacancyName)
                            .category(category)
                            .description(description)
                            .salary(salary)
                            .requiredExperienceMin(minReqExp)
                            .requiredExperienceMax(maxReqExp)
                            .isActive(false)
                            .isPublished(false)
                            .publishedDateTime(LocalDateTime.now())
                    .build());
        }
    }

    public void editVacancy(VacancyDto vacancyDto, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(employerService.getUserIdByEmployerId(vacancyDto.getEmployerId()).equalsIgnoreCase(user.getId())) {
            Vacancy vacancy = makeVacancyFromDto(vacancyDto);
            vacancyDao.editVacancy(vacancy);
        }
    }

    public void deleteVacancy(VacancyDto vacancyDto, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(employerService.getUserIdByEmployerId(vacancyDto.getEmployerId()).equalsIgnoreCase(user.getId())) {
            log.info("Vacancy was deleted: {}", vacancyDto.getId());
            vacancyDao.delete(vacancyDto.getId());
        }
    }

    public ResponseEntity<?> sortedListVacancies(String sortedCriteria) {
        List<Vacancy> list = vacancyDao.getAllVacancies();
        try {
            var sortedVacancies = VacancySortStrategy.fromString(sortedCriteria).sortingVacancies(list);
            return new ResponseEntity<>(
                    sortedVacancies.stream()
                            .map(this::makeDtoFromVacancy)
                            .toList(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


}
