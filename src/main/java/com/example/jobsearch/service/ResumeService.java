package com.example.jobsearch.service;


import com.example.jobsearch.dto.*;
import com.example.jobsearch.entity.Applicant;
import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Resume;
import com.example.jobsearch.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final WorkExperienceService workExperienceService;
    private final EducationService educationService;
    private final ContactInfoService contactInfoService;
    private final ApplicantService applicantService;
    private final UserService userService;
    private final AuthService authService;
    private final CategoryService categoryService;
    private final UtilService utilService;

    private Resume findById(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() -> {
            log.warn("Resume not found: {}", resumeId);
            return new NoSuchElementException("Resume not found");
        });
    }

    public Page<SummaryDto> findSummaryByApplicantId(Long applicantId, int page, int size) {
        List<Resume> applicantResumes = resumeRepository.findByApplicantId(applicantId);
        List<SummaryDto> list = new ArrayList<>();
        for (Resume r : applicantResumes) {
            cleanEmptyTemplate(r);
        }
        List<Resume> applicantResumes2 = resumeRepository.findByApplicantId(applicantId);

        for (Resume r :
                applicantResumes2) {
            list.add(SummaryDto.builder()
                    .id(r.getId())
                    .title(r.getResumeTitle())
                    .isActive(Boolean.toString(r.isActive()))
                    .dateTime(r.getDateTime())
                    .build()
            );
        }
        return utilService.toPage(list, PageRequest.of(page, size));
    }


    private void cleanEmptyTemplate(Resume r) {
        if (r.getResumeTitle() == null && r.getExpectedSalary() == null && r.getCategory() == null) {
            resumeRepository.delete(r);
        }
    }


    private ResumeDto makeDtoFromResume(Resume resume) {
        List<WorkExperienceDto> w = workExperienceService.getDtoListByResumeId(resume.getId());
        List<EducationDto> e = educationService.getDtoListByResumeId(resume.getId());


        ContactInfoDto c = contactInfoService.getDtoByResumeId(resume.getId());

        ApplicantDto applicantDto = applicantService.getApplicantDtoById(resume.getApplicant().getId());

        return ResumeDto.builder()
                .id(resume.getId())
                .profile(applicantDto)
                .resumeTitle(resume.getResumeTitle())
                .category(resume.getCategory().getCategory())
                .expectedSalary(resume.getExpectedSalary())
                .isActive(Boolean.toString(resume.isActive()))
                .isPublished(Boolean.TRUE)
                .eduList(e)
                .workList(w)
                .contact(c)
                .build();
    }

    public ResumeDto findDtoById(Long id) {
        Resume r = findById(id);

        return makeDtoFromResume(r);

    }

    public void edit(Long id, ResumeDto resumeDto, Authentication auth) {
        System.out.println(resumeDto.getIsActive());
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantService.getApplicantByUserEmail(u.getEmail());
        Resume r = findById(id);
        boolean b = Boolean.FALSE;
        if (resumeDto.getIsActive() != null) {
            b = Boolean.TRUE;
        }
        Resume updatedResume = resumeRepository.save(Resume.builder()
                .id(r.getId())
                .applicant(a)
                .resumeTitle(resumeDto.getResumeTitle())
                .category(Category.builder().category(resumeDto.getCategory()).build())
                .expectedSalary(resumeDto.getExpectedSalary())
                .isActive(b)
                .isPublished(Boolean.TRUE)
                .dateTime(LocalDateTime.now())
                .build());
        contactInfoService.saveContactInfo(resumeDto, updatedResume);

    }

    public void addOneWork(Long resumeId, WorkExperienceDto workExperienceDto) {
        Resume r = findById(resumeId);
        workExperienceService.saveWorkExperience(workExperienceDto, r);
    }

    public void addOneEducation(Long resumeId, EducationDto educationDto) {
        Resume r = findById(resumeId);
        educationService.saveEducation(educationDto, r);
    }

    public Long deleteOneWork(Long workExperienceId) {
        return workExperienceService.deleteWorkExperience(workExperienceId);
    }

    public Long deleteOneEducation(Long educationId) {
        return educationService.deleteEducation(educationId);
    }


    @Transactional
    public ResumeDto newResume(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantService.getApplicantByUserEmail(u.getEmail());
        ApplicantDto applicantDto = applicantService.makeDtoFromApplicant(a);
        Resume newResume = resumeRepository.save(Resume.builder()
                .applicant(a)
                .resumeTitle("new resume")
                .category(Category.builder().category("Other").build())
                .expectedSalary(0)
                .isActive(false)
                .isPublished(false)
                .dateTime(LocalDateTime.now())
                .build());

        Resume r = findById(newResume.getId());
        return ResumeDto.builder()
                .id(r.getId())
                .profile(applicantDto)
                .resumeTitle(r.getResumeTitle())
                .category(r.getCategory().getCategory())
                .expectedSalary(r.getExpectedSalary())
                .isActive(Boolean.toString(r.isActive()))
                .isPublished(r.isPublished())
                .eduList(null)
                .workList(null)
                .contact(null)
                .dateTime(r.getDateTime())
                .build();
    }

    public void dateFix(Long id) {
        Resume r = findById(id);
        r.setDateTime(LocalDateTime.now());
        resumeRepository.save(r);
    }

    public UserDto getResumeOwner(Long id) {
        Resume r = findById(id);
        Applicant a = r.getApplicant();
        return userService.getUserDtoLocalStorage(a.getUser().getEmail());
    }

    public List<ResumeDto> findAllByApplicant(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantService.getApplicantByUserEmail(u.getEmail());
        List<Resume> list = resumeRepository.findByApplicantIdAndIsActiveTrue(a.getId());
        return list.stream().map(this::makeDtoFromResume).toList();
    }

    public void delete(Long id) {
        resumeRepository.delete(resumeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Resume does not exist")));

    }

    public Page<ResumeDto> getAll(String sortCriteria, int page, int size, String category, String searchWord) {
        List<Resume> vlist;
        if ("default".equalsIgnoreCase(category) && "default".equalsIgnoreCase(searchWord)) {
            vlist = resumeRepository.findByIsActiveTrue(Sort.by(sortCriteria));
        } else if (!category.equalsIgnoreCase("default") && "default".equalsIgnoreCase(searchWord)) {
            vlist = resumeRepository.findByCategory(categoryService.getByName(category), Sort.by(sortCriteria));
        } else {
            vlist = resumeRepository.customSearchResume(
                    "default".equalsIgnoreCase(category) ? null : categoryService.getByName(category),
                    "default".equalsIgnoreCase(searchWord) ? null : searchWord,
                    Sort.by(sortCriteria)
            );
        }
        List<ResumeDto> dtoList = vlist.stream().map(this::makeDtoFromResume).toList();
        return utilService.toPage(dtoList, PageRequest.of(page, size, Sort.by(sortCriteria)));
    }

    public List<SummaryDto> findSummaryForMain() {
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        List<Resume> l = resumeRepository.findByIsActiveTrue(sort);
        List<SummaryDto> summaryDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            summaryDtos.add(SummaryDto.builder()
                    .id((long) i)
                    .title(l.get(i).getResumeTitle())
                    .dateTime(l.get(i).getDateTime())
                    .build());
        }
        return summaryDtos;
    }

    public int getTotalResumesCount() {
        List<Resume> list = resumeRepository.findAll();
        return list.size();
    }
}

