package com.example.jobsearch.service;


import com.example.jobsearch.dto.*;
import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Employer;
import com.example.jobsearch.entity.Vacancy;
import com.example.jobsearch.repository.CategoryRepository;
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
    private final EmployerService employerService;
    private final UserService userService;
    private final AuthService authService;
    private final CategoryRepository categoryRepository;
private final UtilService utilService;
    private Vacancy getById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(() -> {
            log.warn("Vacancy not found: {}", id);
            return new NoSuchElementException("Vacancy not found");
        });
    }

    private VacancyDto makeDtoFromVacancy(Vacancy v) {
        Employer e = employerService.getEmployerById(v.getEmployer().getId());
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
                .isActive(Boolean.toString(v.isActive()))
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
        Employer e = employerService.getEmployerByUserEmail(u.getEmail());
        boolean b = Boolean.FALSE;
        if(vacancyDto.getIsActive() != null) {
            b=Boolean.TRUE;
        }
        vacancyRepository.save(Vacancy.builder()
                .id(v.getId())
                .employer(e)
                .vacancyName(vacancyDto.getVacancyName())
                .category(Category.builder().category(vacancyDto.getCategory()).build())
                .salary(vacancyDto.getSalary())
                .description(vacancyDto.getDescription())
                .requiredExperienceMin(vacancyDto.getRequiredExperienceMin())
                .requiredExperienceMax(vacancyDto.getRequiredExperienceMax())
                .isActive(b)
                .isPublished(vacancyDto.isPublished())
                .dateTime(LocalDateTime.now())
                .build());
    }

    public void dateFix(Long id) {
        Vacancy v = getById(id);
        v.setDateTime(LocalDateTime.now());
        vacancyRepository.save(v);
    }

    public VacancyDto newVacancy(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Employer e = employerService.getEmployerByUserEmail(u.getEmail());
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
        Employer e = employerService.getEmployerById(v.getEmployer().getId());
        return userService.getUserDtoTest(e.getUser().getEmail());
    }

    public Page<SummaryDto> findSummaryByEmployerId(Long employerId, int page, int size) {
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
                            .isActive(Boolean.toString(v.isActive()))
                    .dateTime(v.getDateTime())
                    .build());
        }
        return utilService.toPage(list, PageRequest.of(page, size));
    }



    public List<Vacancy> findAllByEmployer(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Employer e = employerService.getEmployerByUserEmail(u.getEmail());
        return vacancyRepository.findByEmployerIdAndIsActiveTrue(e.getId());
    }

    private void cleanEmptyTemplate(Vacancy v) {
        if (v.getVacancyName() == null && v.getDescription() == null && v.getCategory() == null && v.getSalary() == null) {
            vacancyRepository.delete(vacancyRepository.findById(v.getId()).orElseThrow(() -> new NoSuchElementException("Vacancy does not exist.")));
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
        if(sortCriteria.equalsIgnoreCase("id")) {
            vlist.sort(Comparator.comparing(Vacancy::getDateTime).reversed());
        }
        var v = vlist.stream().map(this::makeDtoFromVacancy).toList();

        return utilService.toPage(v, PageRequest.of(page, size, Sort.by(sortCriteria)));
    }

    public int getTotalVacanciesCount() {
        List<Vacancy> list = vacancyRepository.findByIsActiveTrue();
        return list.size();
    }

    public List<LocalDate> getDates() {
        List<Vacancy> list = vacancyRepository.findByIsActiveTrue();
        list.sort(Comparator.comparing(Vacancy::getDateTime));
        Set<LocalDate> uniqueDates = new HashSet<>();
        for (Vacancy v :
                list) {
            uniqueDates.add(v.getDateTime().toLocalDate());
        }
        return new ArrayList<>(uniqueDates);
    }

    public Page<CompanyDto> makeCompanyDtos(int pageNumber, int pageSize) {
        List<Employer> elist = employerService.getAllEmployers();
        List<CompanyDto> clist = new ArrayList<>();
        for (Employer e :
                elist) {
            UserDto u = userService.getUserDtoTest(e.getUser().getEmail());
            clist.add(CompanyDto.builder()
                    .photo(u.getPhoto())
                    .name(e.getCompanyName())
                    .phoneNumber(u.getPhoneNumber())
                    .email(u.getEmail())
                    .build());
        }
        System.out.println("cList" + clist.size());

        return utilService.toPage(clist, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "email")));

    }

    public CompanyDto getCompanyDto(String email) {
        UserDto u = userService.getUserDtoTest(email);
        Employer e = employerService.getEmployerByUserEmail(email);
        return CompanyDto.builder()
                .photo(u.getPhoto())
                .name(e.getCompanyName())
                .phoneNumber(u.getPhoneNumber())
                .email(u.getEmail())
                .build();
    }

    public Page<SummaryDto> findSummaryByEmployerEmail(String email, int page, int size) {
        Employer e = employerService.getEmployerByUserEmail(email);
        List<Vacancy> employerVacancies = vacancyRepository.findByEmployerIdAndIsActiveTrue(e.getId());
        List<SummaryDto> list = new ArrayList<>();
        for (Vacancy v : employerVacancies) {
            cleanEmptyTemplate(v);
        }
        List<Vacancy> employerVacancies2 = vacancyRepository.findByEmployerIdAndIsActiveTrue(e.getId());

        for (Vacancy v : employerVacancies2) {
            list.add(SummaryDto.builder()
                    .id(v.getId())
                    .title(v.getVacancyName())
                    .dateTime(v.getDateTime())
                    .build());
        }
        return utilService.toPage(list, PageRequest.of(page, size));
    }

    public int getCompanyDtoSize() {
        var e = employerService.getAllEmployers();
        return e.size();
    }

    public int getEmployerVacancyCount(String email) {
        Employer e = employerService.getEmployerByUserEmail(email);
        List<Vacancy> employerVacancies = vacancyRepository.findByEmployerIdAndIsActiveTrue(e.getId());
        return employerVacancies.size();
    }

    public int getEmployerVacancyCount(Long id) {
        Employer e = employerService.getEmployerById(id);
        List<Vacancy> employerVacancies = vacancyRepository.findByEmployerId(e.getId());
        return employerVacancies.size();
    }
}
