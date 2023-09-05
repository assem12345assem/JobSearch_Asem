package com.example.jobsearch.service;


import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.SummaryDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Employer;
import com.example.jobsearch.entity.Vacancy;
import com.example.jobsearch.repository.CategoryRepository;
import com.example.jobsearch.repository.EmployerRepository;
import com.example.jobsearch.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final EmployerRepository employerRepository;
    private final UserService userService;
    private final AuthService authService;
    private final CategoryRepository categoryRepository;

    private Vacancy getById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(() -> {
            log.warn("Vacancy not found: {}", id);
            return new NoSuchElementException("Vacancy not found");
        });
    }

    private VacancyDto makeDtoFromVacancy(Vacancy v) {
        Employer e = employerRepository.findById(v.getEmployer().getId()).orElseThrow(() -> {
            log.warn("Employer not found: {}", v.getEmployer().getId());
            return new NoSuchElementException("Employer not found");
        });
        String categoryValue = (v.getCategory() != null) ? v.getCategory().getCategory() : null;

        return VacancyDto.builder()
                .id(v.getId())
                .profile(EmployerDto.builder()
                        .id(e.getId())
                        .companyName(e.getCompanyName())
                        .build())
                .vacancyName(v.getVacancyName())
                .category(categoryValue)
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
        Employer e = employerRepository.findByUserEmail(u.getEmail()).orElseThrow(() ->
                new NoSuchElementException("Employer is not found."));
        vacancyRepository.save(Vacancy.builder()
                .id(v.getId())
                .employer(e)
                .vacancyName(vacancyDto.getVacancyName())
                .category(Category.builder().category(vacancyDto.getCategory()).build())
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
        vacancyRepository.save(Vacancy.builder()
                .id(v.getId())
                .employer(v.getEmployer())
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
        Employer e = employerRepository.findByUserEmail(u.getEmail()).orElseThrow(() -> {
            log.warn("Employer not found: {}", u.getEmail());
            return new NoSuchElementException("Employer not found");
        });
        Vacancy v = vacancyRepository.save(Vacancy.builder()
                .employer(e)
                .vacancyName(null)
                .category(Category.builder().category("Other").build())
                .salary(0)
                .description(null)
                .requiredExperienceMin(0)
                .requiredExperienceMax(0)
                .isActive(Boolean.FALSE)
                .isPublished(Boolean.FALSE)
                .dateTime(LocalDateTime.now())
                .build());
        return makeDtoFromVacancy(v);
    }

    public UserDto getVacancyOwner(Long id) {
        Vacancy v = getById(id);
        Employer e = employerRepository.findById(v.getEmployer().getId()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        return userService.getUserDtoTest(e.getUser().getEmail());
    }

    public List<SummaryDto> findSummaryByEmployerId(Long employerId) {
        List<Vacancy> employerVacancies = vacancyRepository.findByEmployerId(employerId);
        List<SummaryDto> list = new ArrayList<>();
        for (Vacancy v : employerVacancies) {
            cleanEmptyTemplate(v);
        }
        List<Vacancy> employerVacancies2 = vacancyRepository.findByEmployerId(employerId);

        for (Vacancy v : employerVacancies2) {
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
        Employer e = employerRepository.findByUserEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        return vacancyRepository.findByEmployerId(e.getId());
    }

    private void cleanEmptyTemplate(Vacancy v) {
        if (v.getVacancyName() == null) {
            if (v.getDescription() == null) {
                if (v.getCategory() == null) {
                    if (v.getSalary() == null) {
                        vacancyRepository.delete(vacancyRepository.findById(v.getId()).orElseThrow(() -> new NoSuchElementException("Vacancy does not exist.")));
                    }
                }
            }
        }

    }

    public void delete(Long id) {
        vacancyRepository.delete(vacancyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Vacancy does not exist.")));
    }

    public List<SummaryDto> findSummaryForMain() {
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        List<Vacancy> l = vacancyRepository.findAll(sort);
        List<SummaryDto> summaryDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            summaryDtos.add(SummaryDto.builder()
                    .id((long) i)
                    .title(l.get(i).getVacancyName())
                    .dateTime(l.get(i).getDateTime())
                    .build());
        }
        return summaryDtos;
    }

public Page<VacancyDto> getAll(String sortCriteria, int page, int size, String category, String date, String application, String searchWord) {
    List<Vacancy> vlist;
    if ("default".equalsIgnoreCase(category) && "default".equalsIgnoreCase(date) && "default".equalsIgnoreCase(application) && "default".equalsIgnoreCase(searchWord)) {
        vlist = vacancyRepository.findAll(Sort.by(sortCriteria));
    } else if (!category.equalsIgnoreCase("default") && "default".equalsIgnoreCase(date) && "default".equalsIgnoreCase(application) && "default".equalsIgnoreCase(searchWord)) {
        vlist = vacancyRepository.findByCategory(categoryRepository.findById(category).get(), Sort.by(sortCriteria));
    } else {
        vlist = vacancyRepository.findByCategoryAndDateAndApplicationAndSearchWord(
                "default".equalsIgnoreCase(category) ? null : categoryRepository.findById(category).orElse(null),
                "default".equalsIgnoreCase(date) ? null : LocalDate.parse(date),
                "default".equalsIgnoreCase(application) ? null : Integer.parseInt(application),
                "default".equalsIgnoreCase(searchWord) ? null : searchWord,
                Sort.by(sortCriteria)
        );
    }
    return toPage(vlist, PageRequest.of(page, size, Sort.by(sortCriteria)));
}


    private Page<VacancyDto> toPage(List<Vacancy> list, Pageable pageable) {
        var v = list.stream().map(this::makeDtoFromVacancy).toList();
        if (pageable.getOffset() >= v.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min((int) (startIndex + pageable.getPageSize()), v.size());
        List<VacancyDto> subList = v.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, v.size());
    }

    public int getTotalVacanciesCount() {
        List<Vacancy> list = vacancyRepository.findAll();
        return list.size();
    }

    public Page<VacancyDto> getAllByDateReversed(String sortCriteria, int pageNumber, int pageSize) {
        var vlist = vacancyRepository.findAll();
        return toPage(vlist, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortCriteria)));
    }

    public List<LocalDate> getDates() {
        List<Vacancy> list = vacancyRepository.findAll();
        list.sort(Comparator.comparing(Vacancy::getDateTime));
        Set<LocalDate> uniqueDates = new HashSet<>();
        for (Vacancy v :
                list) {
            uniqueDates.add(v.getDateTime().toLocalDate());
        }
        return new ArrayList<>(uniqueDates);
    }

}
