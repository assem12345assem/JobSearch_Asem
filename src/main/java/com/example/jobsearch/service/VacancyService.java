package com.example.jobsearch.service;

import com.example.demo.dao.EmployerDao;
import com.example.demo.dao.VacancyDao;
import com.example.demo.dto.EmployerDto;
import com.example.demo.dto.SummaryDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.VacancyDto;
import com.example.demo.entity.Employer;
import com.example.demo.entity.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyDao vacancyDao;
    private final EmployerDao employerDao;
    private final UserService userService;
    private final AuthService authService;
private Vacancy getById(Long id) {
    return vacancyDao.find(id).orElseThrow(() -> {
        log.warn("Vacancy not found: {}", id);
        return new NoSuchElementException("Vacancy not found");
    });
}
private VacancyDto makeDtoFromVacancy(Vacancy v) {
    Employer e = employerDao.find(v.getEmployerId()).orElseThrow(() -> {
        log.warn("Employer not found: {}", v.getEmployerId());
        return new NoSuchElementException("Employer not found");
    });

    return VacancyDto.builder()
            .id(v.getId())
            .profile(EmployerDto.builder()
                    .id(e.getId())
                    .companyName(e.getCompanyName())
                    .build())
            .vacancyName(v.getVacancyName())
            .category(v.getCategory())
            .salary(v.getSalary())
            .description(v.getDescription())
            .requiredExperienceMin(v.getRequiredExperienceMin())
            .requiredExperienceMax(v.getRequiredExperienceMax())
            .isActive(Boolean.TRUE)
            .isPublished(Boolean.TRUE)
            .dateTime(v.getDateTime())
            .build();
}
    public VacancyDto findDtoById(Long id) {
        Vacancy v = getById(id);
        return makeDtoFromVacancy(v);
    }

    public void edit(Long id, VacancyDto vacancyDto, Authentication auth) {
    Vacancy v = getById(id);
    UserDto u = authService.getAuthor(auth);
    Employer e = employerDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer is not found."));
    vacancyDao.update(Vacancy.builder()
                    .id (v.getId())
                    .employerId(e.getId())
                    .vacancyName(vacancyDto.getVacancyName())
                    .category(vacancyDto.getCategory())
                    .salary(vacancyDto.getSalary())
                    .description(vacancyDto.getDescription())
                    .requiredExperienceMin(vacancyDto.getRequiredExperienceMin())
                    .requiredExperienceMax(vacancyDto.getRequiredExperienceMax())
                    .isActive(vacancyDto.isActive())
                    .isPublished(vacancyDto.isPublished())
                    .dateTime(LocalDateTime.now())
            .build());
    }

    public void dateFix(Long id) {
        Vacancy v = getById(id);
        vacancyDao.update(Vacancy.builder()
                .id (v.getId())
                .employerId(v.getId())
                .vacancyName(v.getVacancyName())
                .category(v.getCategory())
                .salary(v.getSalary())
                .description(v.getDescription())
                .requiredExperienceMin(v.getRequiredExperienceMin())
                .requiredExperienceMax(v.getRequiredExperienceMax())
                .isActive(v.isActive())
                .isPublished(v.isPublished())
                .dateTime(LocalDateTime.now())
                .build());
    }

    public VacancyDto newVacancy(Authentication auth) {
    UserDto u = authService.getAuthor(auth);
        Employer e = employerDao.findByEmail(u.getEmail()).orElseThrow(() -> {
            log.warn("Employer not found: {}", u.getEmail());
            return new NoSuchElementException("Employer not found");
        });
        Vacancy v = Vacancy.builder()
                .employerId(e.getId())
                .vacancyName(null)
                .category("Other")
                .salary(0)
                .description(null)
                .requiredExperienceMin(0)
                .requiredExperienceMax(0)
                .isActive(Boolean.FALSE)
                .isPublished(Boolean.FALSE)
                .dateTime(LocalDateTime.now())
                .build();
        Long id = vacancyDao.save(v);
        Vacancy va = getById(id);
        return makeDtoFromVacancy(va);
    }

    public UserDto getVacancyOwner(Long id) {
    Vacancy v = getById(id);
    Employer e = employerDao.find(v.getEmployerId()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        return userService.getUserDtoTest(e.getUserId());
    }

    public List<SummaryDto> findSummaryByEmployerId(Long employerId) {
    List<Vacancy> employerVacancies = vacancyDao.findByEmployerId(employerId);
    List<SummaryDto> list = new ArrayList<>();
    for(Vacancy v : employerVacancies) {
        cleanEmptyTemplate(v);
    }
        List<Vacancy> employerVacancies2 = vacancyDao.findByEmployerId(employerId);

        for(Vacancy v: employerVacancies2) {
        list.add(SummaryDto.builder()
                        .id(v.getId())
                        .title(v.getVacancyName())
                        .dateTime(v.getDateTime())
                .build());
    }
    return list;
    }
    public List<Vacancy> findAllByEmployer(Authentication auth) {
    UserDto u = authService.getAuthor(auth);
    var e = employerDao.findByEmail(u.getEmail());
        return e.map(employer -> vacancyDao.findByEmployerId(employer.getId())).orElse(null);
    }
    private void cleanEmptyTemplate(Vacancy v){
        if (v.getVacancyName() == null) {
            if (v.getDescription() == null) {
                if (v.getCategory() == null) {
                    if (v.getSalary() == null) {
                        vacancyDao.delete(v.getId());
                    }
                }
            }
        }

    }

    public void delete(Long id) {
    vacancyDao.delete(id);
    }

    public List<VacancyDto> getAll() {
    List<Vacancy> v = vacancyDao.getAll();
    return v.stream().map(this::makeDtoFromVacancy).toList();
    }

    public List<VacancyDto> getAllByDate() {
    List<VacancyDto> v = getAll();
        List<VacancyDto> notEmptyField = new ArrayList<>();
        List<VacancyDto> emptyField = new ArrayList<>();
        for (VacancyDto vacancy:
             v) {
            if(vacancy.getDateTime() == null) {
                emptyField.add(vacancy);
            } else {
                notEmptyField.add(vacancy);
            }
        }

        notEmptyField.sort(Comparator.comparing(VacancyDto::getDateTime));

        notEmptyField.addAll(emptyField);
        return notEmptyField;
    }
    public List<VacancyDto> getAllByDateReversed() {
        List<VacancyDto> v = getAll();
        List<VacancyDto> notEmptyField = new ArrayList<>();
        List<VacancyDto> emptyField = new ArrayList<>();
        for (VacancyDto vacancy:
                v) {
            if(vacancy.getDateTime() == null) {
                emptyField.add(vacancy);
            } else {
                notEmptyField.add(vacancy);
            }
        }

        notEmptyField.sort(Comparator.comparing(VacancyDto::getDateTime).reversed());

        notEmptyField.addAll(emptyField);
        return notEmptyField;
    }

    public List<VacancyDto> getAllBySalary() {
        List<VacancyDto> v = getAll();
        List<VacancyDto> notEmptyField = new ArrayList<>();
        List<VacancyDto> emptyField = new ArrayList<>();
        for (VacancyDto vacancy:
                v) {
            if(vacancy.getSalary() == null) {
                emptyField.add(vacancy);
            } else {
                notEmptyField.add(vacancy);
            }
        }

        notEmptyField.sort(Comparator.comparing(VacancyDto::getSalary).reversed());

        notEmptyField.addAll(emptyField);
        return notEmptyField;
    }

    public List<VacancyDto> filterByCategory(String category) {
    List<VacancyDto> v = getAll();
    List<VacancyDto> v2 = new ArrayList<>();
        for (VacancyDto vacancy:
            v ) {
            if(vacancy.getCategory() != null) {
                if(vacancy.getCategory().equalsIgnoreCase(category)) {
                    v2.add(vacancy);
                }
            }

        }
        return v2;
    }

    public List<VacancyDto> searchResult(String searchWord) {
    String search = searchWord.toLowerCase();
    List<VacancyDto> v = getAll();
    List<VacancyDto> searchResult = new ArrayList<>();
        for (VacancyDto vacancy:
             v) {
            if(vacancy.getVacancyName() !=null) {
                if(vacancy.getVacancyName().toLowerCase().contains(search)) {
                    searchResult.add(vacancy);
                }
            }
            if(vacancy.getSalary() != null) {
                if(vacancy.getSalary().toString().contains(search)) {
                    searchResult.add(vacancy);
                }
            }
            if(vacancy.getDescription() != null) {
                if(vacancy.getDescription().toLowerCase().contains(search)) {
                    searchResult.add(vacancy);
                }
            }
        }
        return searchResult;
    }

    public List<SummaryDto> findSummaryForMain() {
    List<VacancyDto> l = getAllByDateReversed();
    List<SummaryDto> summaryDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            summaryDtos.add(SummaryDto.builder()
                            .id((long)i)
                            .title(l.get(i).getVacancyName())
                            .dateTime(l.get(i).getDateTime())
                    .build());
        }
        return summaryDtos;
    }
}
