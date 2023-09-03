package com.example.jobsearch.service;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.enums.VacancySortStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyDao vacancyDao;
    private final EmployerProfileService employerService;
    private final UserService userService;
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
        if (list.isEmpty()) {
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

    public ResponseEntity<?> createVacancy(VacancyDto vacancyDto, Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = userService.getUserFromAuth(u.toString());
        if (employerService.getUserIdByEmployerId(vacancyDto.getEmployerId()).equalsIgnoreCase(user.get().getId())) {
            Optional<Vacancy> v;
            if (vacancyDto.getId() == null) {
                long x = (long) vacancyDao.getAllVacancies().size() + 1;
                v = vacancyDao.findVacancyById(x);
            } else {
                v = vacancyDao.findVacancyById(vacancyDto.getId());
            }
            if (v.isEmpty()) {
                if (categoryService.getCategory(vacancyDto.getCategory()).isPresent()) {
                    vacancyDao.save(makeVacancyFromDto(vacancyDto));
                    return new ResponseEntity<>("Vacancy was created successfully", HttpStatus.OK);
                } else {
                    log.warn("Tried to use a category that does not exist: {}", vacancyDto.getCategory());
                    return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);
                }
            } else {
                log.info("Tried to create a vacancy that already exists: {}", vacancyDto.getId());
                return new ResponseEntity<>("Vacancy already exists", HttpStatus.OK);
            }
        } else {
            log.warn("Tried to create a vacancy for another user: {}", user.get().getId());
            return new ResponseEntity<>("Tried to create a vacancy for another user", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> editVacancy(VacancyDto vacancyDto, Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = userService.getUserFromAuth(u.toString());
        if (employerService.getUserIdByEmployerId(vacancyDto.getEmployerId()).equalsIgnoreCase(user.get().getId())) {
            if (vacancyDto.getId() == null) {
                return new ResponseEntity<>("Cannot edit a vacancy without vacancy id", HttpStatus.NOT_FOUND);
            }
            var v = vacancyDao.findVacancyById(vacancyDto.getId());
            if (v.isEmpty()) {
                log.info("Tried to edit a vacancy that that does not exist: {}", vacancyDto.getId());
                return new ResponseEntity<>("Vacancy does not exist", HttpStatus.OK);
            } else {
                if(categoryService.getCategory(vacancyDto.getCategory()).isPresent()) {
                    Vacancy vacancy = makeVacancyFromDto(vacancyDto);
                    vacancyDao.editVacancy(vacancy);
                    return new ResponseEntity<>("Vacancy edited successfully", HttpStatus.OK);
                }else {
                    log.warn("Tried to use a category that does not exist: {}", vacancyDto.getCategory());
                    return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            log.warn("Tried to edit a vacancy of another user: {}", user.get().getId());
            return new ResponseEntity<>("Tried to edit a vacancy of another user", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteVacancy(VacancyDto vacancyDto, Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = userService.getUserFromAuth(u.toString());
        if (employerService.getUserIdByEmployerId(vacancyDto.getEmployerId()).equalsIgnoreCase(user.get().getId())) {
            var v = vacancyDao.findVacancyById(vacancyDto.getId());
            if (v.isEmpty()) {
                log.info("Tried to delete a vacancy that does not exist: {}", vacancyDto.getId());
                return new ResponseEntity<>("Tried to delete a vacancy that does not exist", HttpStatus.OK);
            } else {
                vacancyDao.delete(vacancyDto.getId());
                log.info("Vacancy was deleted: {}", vacancyDto.getId());
                return new ResponseEntity<>("Vacancy was deleted successfully", HttpStatus.OK);

            }

        } else {
            log.warn("Tried to delete a vacancy of another user: {}", user.get().getId());
            return new ResponseEntity<>("Tried to delete a vacancy of another user", HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<?> findAllVacanciesByEmployerId(Long employerId) {
        List<Vacancy> list = vacancyDao.getAllVacanciesByEmployerId(employerId);
        if (list.isEmpty()) {
            return new ResponseEntity<>("There are no vacancies by this employer", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list.stream()
                    .map(this::makeDtoFromVacancy)
                    .toList(), HttpStatus.OK);
        }
    }
    public List<VacancyDto> getAllVacanciesByEmployerId(Long employerId)  {
        List<Vacancy> list = vacancyDao.getAllVacanciesByEmployerId(employerId);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.stream()
                    .map(this::makeDtoFromVacancy)
                    .toList();
        }
    }

    public void create(String userId, VacancyDto vacancyDto) {
        vacancyDao.save(Vacancy.builder()
                        .employerId(employerService.getEmployerByUserId(userId).get().getId())
                .vacancyName(vacancyDto.getVacancyName())
                .category(vacancyDto.getCategory())
                .salary(vacancyDto.getSalary())
                .description(vacancyDto.getDescription())
                .requiredExperienceMin(vacancyDto.getRequiredExperienceMin())
                .requiredExperienceMax(vacancyDto.getRequiredExperienceMax())
                .isActive(Boolean.TRUE)
                .isPublished(Boolean.TRUE)
                        .publishedDateTime(LocalDateTime.now())
                .build());
    }
}
