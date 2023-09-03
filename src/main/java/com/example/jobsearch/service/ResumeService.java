package com.example.jobsearch.service;


import com.example.jobsearch.dto.*;
import com.example.jobsearch.entity.*;
import com.example.jobsearch.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ApplicantRepository applicantRepository;
    private final UserService userService;
    private final AuthService authService;

    private Resume findById(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() -> {
            log.warn("Resume not found: {}", resumeId);
            return new NoSuchElementException("Resume not found");
        });
    }

    public List<SummaryDto> findSummaryByApplicantId(Long applicantId) {
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
                    .dateTime(r.getDateTime())
                    .build()
            );
        }
        return list;
    }

    private void cleanEmptyTemplate(Resume r) {
        if (r.getResumeTitle() == null) {
            if (r.getExpectedSalary() == null) {
                if (r.getCategory() == null) {
                    resumeRepository.delete(r);
                }
            }
        }

    }

    private ResumeDto makeDtoFromResume(Resume resume) {
        List<WorkExperienceDto> w = workExperienceRepository.findByResumeId(resume.getId()).stream()
                .map(e -> WorkExperienceDto.builder()
                        .id(e.getId())
                        .dateStart(e.getDateStart())
                        .dateEnd(e.getDateEnd())
                        .companyName(e.getCompanyName())
                        .position(e.getPosition())
                        .responsibilities(e.getResponsibilities())
                        .build())
                .collect(Collectors.toList());
        List<EducationDto> e = educationRepository.findByResumeId(resume.getId()).stream()
                .map(education -> EducationDto.builder()
                        .id(education.getId())
                        .education(education.getEducation())
                        .schoolName(education.getSchoolName())
                        .startDate(education.getStartDate())
                        .graduationDate(education.getGraduationDate())
                        .build())
                .collect(Collectors.toList());
        var contactInfoVar = contactInfoRepository.findByResumeId(resume.getId());
        ContactInfo contactInfo;
        ContactInfoDto c;
        if (contactInfoVar.isEmpty()) c = null;
        else {
            contactInfo = contactInfoVar.get();
            c = ContactInfoDto.builder()
                    .telegram(contactInfo.getTelegram())
                    .email(contactInfo.getEmail())
                    .phoneNumber(contactInfo.getPhoneNumber())
                    .facebook(contactInfo.getFacebook())
                    .linkedIn(contactInfo.getLinkedIn())
                    .build();
        }
        Applicant a = applicantRepository.findById(resume.getApplicant().getId()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        ApplicantDto applicantDto = ApplicantDto.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
        return ResumeDto.builder()
                .id(resume.getId())
                .profile(applicantDto)
                .resumeTitle(resume.getResumeTitle())
                .category(resume.getCategory().getCategory())
                .expectedSalary(resume.getExpectedSalary())
                .isActive(Boolean.TRUE)
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
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantRepository.findByUserId(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        Resume r = findById(id);
        Resume updatedResume = resumeRepository.save(Resume.builder()
                .id(r.getId())
                .applicant(a)
                .resumeTitle(resumeDto.getResumeTitle())
                .category(Category.builder().category(resumeDto.getCategory()).build())
                .expectedSalary(resumeDto.getExpectedSalary())
                .isActive(Boolean.TRUE)
                .isPublished(Boolean.TRUE)
                .dateTime(LocalDateTime.now())
                .build());
        var contactInfoVar = contactInfoRepository.findByResumeId(r.getId());
        if (contactInfoVar.isEmpty()) {
            contactInfoRepository.save(ContactInfo.builder()
                    .resume(updatedResume)
                    .telegram(null)
                    .email(null)
                    .phoneNumber(null)
                    .facebook(null)
                    .linkedIn(null)
                    .build());

        }

        contactInfoRepository.save(ContactInfo.builder()
                .id(contactInfoVar.get().getId())
                .resume(updatedResume)
                .telegram(resumeDto.getContact().getTelegram())
                .email(resumeDto.getContact().getEmail())
                .phoneNumber(resumeDto.getContact().getPhoneNumber())
                .facebook(resumeDto.getContact().getFacebook())
                .linkedIn(resumeDto.getContact().getLinkedIn())
                .build());
    }

    public void addOneWork(Long resumeId, WorkExperienceDto workExperienceDto) {
        Resume r = findById(resumeId);
        workExperienceRepository.save(WorkExperience.builder()
                .resume(r)
                .dateStart(workExperienceDto.getDateStart())
                .dateEnd(workExperienceDto.getDateEnd())
                .companyName(workExperienceDto.getCompanyName())
                .position(workExperienceDto.getPosition())
                .responsibilities(workExperienceDto.getResponsibilities())
                .build());
    }

    public void addOneEducation(Long resumeId, EducationDto educationDto) {
        Resume r = findById(resumeId);
        educationRepository.save(Education.builder()
                .resume(r)
                .education(educationDto.getEducation())
                .schoolName(educationDto.getSchoolName())
                .startDate(educationDto.getStartDate())
                .graduationDate(educationDto.getGraduationDate())
                .build());
    }

    public Long deleteOneWork(Long workExperienceId) {
        WorkExperience w = workExperienceRepository.findById(workExperienceId).orElseThrow(() -> {
            log.warn("Work experience not found: {}", workExperienceId);
            return new NoSuchElementException("Work experience not found");
        });
        workExperienceRepository.delete(w);
        return w.getResume().getId();
    }

    public Long deleteOneEducation(Long educationId) {
        Education e = educationRepository.findById(educationId).orElseThrow(() -> {
            log.warn("Education not found: {}", educationId);
            return new NoSuchElementException("Education not found");
        });
        educationRepository.delete(e);
        return e.getResume().getId();
    }

    public ResumeDto newResume(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantRepository.findByUserId(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        ApplicantDto applicantDto = ApplicantDto.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
        Resume newResume = resumeRepository.save(Resume.builder()
                .applicant(a)
                .resumeTitle(null)
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
                .isActive(Boolean.TRUE)
                .isPublished(Boolean.TRUE)
                .eduList(null)
                .workList(null)
                .contact(null)
                .dateTime(r.getDateTime())
                .build();
    }

    public void dateFix(Long id) {
        Resume r = findById(id);
        resumeRepository.save(Resume.builder()
                .id(r.getId())
                .applicant(r.getApplicant())
                .resumeTitle(r.getResumeTitle())
                .category(r.getCategory())
                .expectedSalary(r.getExpectedSalary())
                .isActive(Boolean.TRUE)
                .isPublished(Boolean.TRUE)
                .dateTime(LocalDateTime.now())
                .build());
    }

    public UserDto getResumeOwner(Long id) {
        Resume r = findById(id);
        Applicant a = r.getApplicant();
        return userService.getUserDtoLocalStorage(a.getUser().getEmail());
    }

    public List<ResumeDto> findAllByApplicant(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantRepository.findByUserId(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        List<Resume> list = resumeRepository.findByApplicantId(a.getId());
        return list.stream().map(this::makeDtoFromResume).toList();
    }

    public void delete(Long id) {
        resumeRepository.delete(resumeRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("Resume does not exist")));

    }

    public List<ResumeDto> getAll() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream().map(this::makeDtoFromResume).toList();
    }

    public List<ResumeDto> searchResult(String searchWord) {
        List<ResumeDto> r = getAll();
        String search = searchWord.toLowerCase();
        List<ResumeDto> searchResult = new ArrayList<>();
        for (ResumeDto resume :
                r) {
            if (resume.getResumeTitle() != null) {
                if (resume.getResumeTitle().toLowerCase().contains(search)) {
                    searchResult.add(resume);
                }
            }
            if (resume.getExpectedSalary() != null) {
                if (resume.getExpectedSalary().toString().contains(search)) {
                    searchResult.add(resume);
                }
            }
        }
        return searchResult;
    }

    public List<ResumeDto> getAllByDateReversed() {
        List<ResumeDto> r = getAll();
        List<ResumeDto> notEmptyField = new ArrayList<>();
        List<ResumeDto> emptyField = new ArrayList<>();
        for (ResumeDto resume :
                r) {
            if (resume.getDateTime() == null) {
                emptyField.add(resume);
            } else {
                notEmptyField.add(resume);
            }
        }

        notEmptyField.sort(Comparator.comparing(ResumeDto::getDateTime).reversed());

        notEmptyField.addAll(emptyField);
        return notEmptyField;
    }

    public List<ResumeDto> filterByCategory(String category) {
        List<ResumeDto> r = getAll();
        List<ResumeDto> r2 = new ArrayList<>();
        for (ResumeDto resume :
                r) {
            if (resume.getCategory() != null) {
                if (resume.getCategory().equalsIgnoreCase(category)) {
                    r2.add(resume);
                }
            }

        }
        return r2;
    }

    public List<SummaryDto> findSummaryForMain() {
        List<ResumeDto> l = getAllByDateReversed();
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
}

