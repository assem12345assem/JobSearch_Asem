package com.example.jobsearch.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
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
    private final ResumeDao resumeDao;
    private final WorkExperienceDao workExperienceDao;
    private final EducationDao educationDao;
    private final ContactInfoDao contactInfoDao;
    private final ApplicantDao applicantDao;
    private final UserService userService;
    private final AuthService authService;

    private Resume findById(Long resumeId) {
        return resumeDao.find(resumeId).orElseThrow(() -> {
            log.warn("Resume not found: {}", resumeId);
            return new NoSuchElementException("Resume not found");
        });
    }

    public List<SummaryDto> findSummaryByApplicantId(Long applicantId) {
        List<Resume> applicantResumes = resumeDao.findByApplicantId(applicantId);
        List<SummaryDto> list = new ArrayList<>();
        for(Resume r: applicantResumes) {
            cleanEmptyTemplate(r);
        }
        List<Resume> applicantResumes2 = resumeDao.findByApplicantId(applicantId);

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
                resumeDao.delete(r.getId());
            }
        }
    }

}
    private ResumeDto makeDtoFromResume(Resume resume) {
        List<WorkExperienceDto> w = workExperienceDao.findByResumeId(resume.getId()).stream()
                .map(e -> WorkExperienceDto.builder()
                        .id(e.getId())
                        .dateStart(e.getDateStart())
                        .dateEnd(e.getDateEnd())
                        .companyName(e.getCompanyName())
                        .position(e.getPosition())
                        .responsibilities(e.getResponsibilities())
                        .build())
                .collect(Collectors.toList());
        List<EducationDto> e = educationDao.findByResumeId(resume.getId()).stream()
                .map(education -> EducationDto.builder()
                        .id(education.getId())
                        .education(education.getEducation())
                        .schoolName(education.getSchoolName())
                        .startDate(education.getStartDate())
                        .graduationDate(education.getGraduationDate())
                        .build())
                .collect(Collectors.toList());
        var contactInfoVar = contactInfoDao.findByResumeId(resume.getId());
        ContactInfo contactInfo;
        ContactInfoDto  c;
        if(contactInfoVar.isEmpty()) c = null;
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
        Applicant a = applicantDao.find(resume.getApplicantId()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        ApplicantDto applicantDto = ApplicantDto.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
        return ResumeDto.builder()
                .id(resume.getId())
                .profile(applicantDto)
                .resumeTitle(resume.getResumeTitle())
                .category(resume.getCategory())
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
        Applicant a = applicantDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        Resume r = findById(id);
        resumeDao.update(Resume.builder()
                .id(r.getId())
                .applicantId(a.getId())
                .resumeTitle(resumeDto.getResumeTitle())
                .category(resumeDto.getCategory())
                .expectedSalary(resumeDto.getExpectedSalary())
                .isActive(Boolean.TRUE)
                .isPublished(Boolean.TRUE)
                .dateTime(LocalDateTime.now())
                .build());
        var contactInfoVar = contactInfoDao.findByResumeId(r.getId());
        Long c_id;
        if(contactInfoVar.isEmpty()) {
            c_id = contactInfoDao.save(ContactInfo.builder()
                            .resumeId(id)
                    .telegram(null)
                    .email(null)
                    .phoneNumber(null)
                    .facebook(null)
                    .linkedIn(null)
                    .build());

        }
        else {
           c_id = contactInfoVar.get().getId();
        }
        contactInfoDao.update(ContactInfo.builder()
                .id(c_id)
                .resumeId(id)
                .telegram(resumeDto.getContact().getTelegram())
                .email(resumeDto.getContact().getEmail())
                .phoneNumber(resumeDto.getContact().getPhoneNumber())
                .facebook(resumeDto.getContact().getFacebook())
                .linkedIn(resumeDto.getContact().getLinkedIn())
                .build());
    }

    public void addOneWork(Long resumeId, WorkExperienceDto workExperienceDto) {
        Resume r = findById(resumeId);
        workExperienceDao.save(WorkExperience.builder()
                .resumeId(r.getId())
                .dateStart(workExperienceDto.getDateStart())
                .dateEnd(workExperienceDto.getDateEnd())
                .companyName(workExperienceDto.getCompanyName())
                .position(workExperienceDto.getPosition())
                .responsibilities(workExperienceDto.getResponsibilities())
                .build());
    }

    public void addOneEducation(Long resumeId, EducationDto educationDto) {
        Resume r = findById(resumeId);
        educationDao.save(Education.builder()
                .resumeId(r.getId())
                .education(educationDto.getEducation())
                .schoolName(educationDto.getSchoolName())
                .startDate(educationDto.getStartDate())
                .graduationDate(educationDto.getGraduationDate())
                .build());
    }

    public Long deleteOneWork(Long workExperienceId) {
        WorkExperience w = workExperienceDao.find(workExperienceId).orElseThrow(() -> {
            log.warn("Work experience not found: {}", workExperienceId);
            return new NoSuchElementException("Work experience not found");
        });
        workExperienceDao.delete(w.getId());
        return w.getResumeId();
    }

    public Long deleteOneEducation(Long educationId) {
        Education e = educationDao.find(educationId).orElseThrow(() -> {
            log.warn("Education not found: {}", educationId);
            return new NoSuchElementException("Education not found");
        });
        educationDao.delete(e.getId());
        return e.getResumeId();
    }

    public ResumeDto newResume(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        ApplicantDto applicantDto = ApplicantDto.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
        Long newResumeId = resumeDao.save(Resume.builder()
                .applicantId(a.getId())
                .resumeTitle(null)
                .category("Other")
                .expectedSalary(0)
                .isActive(false)
                .isPublished(false)
                .dateTime(LocalDateTime.now())
                .build());


        Resume r = findById(newResumeId);
        return ResumeDto.builder()
                .id(newResumeId)
                .profile(applicantDto)
                .resumeTitle(r.getResumeTitle())
                .category(r.getCategory())
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
        resumeDao.update(Resume.builder()
                .id(r.getId())
                .applicantId(r.getApplicantId())
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
        Applicant a = applicantDao.find(r.getApplicantId()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        return userService.getUserDtoLocalStorage(a.getUserId());
    }

    public List<ResumeDto> findAllByApplicant(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        Applicant a = applicantDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
        List<Resume> list = resumeDao.findByApplicantId(a.getId());
        return list.stream().map(this::makeDtoFromResume).toList();
    }

    public void delete(Long id) {
        resumeDao.delete(id);
    }

    public List<ResumeDto> getAll() {
        List<Resume> resumes = resumeDao.getAll();
        return resumes.stream().map(this::makeDtoFromResume).toList();
    }

    public List<ResumeDto> searchResult(String searchWord) {
        List<ResumeDto> r = getAll();
        String search = searchWord.toLowerCase();
        List<ResumeDto> searchResult = new ArrayList<>();
        for (ResumeDto resume:
               r) {
            if(resume.getResumeTitle() !=null) {
                if(resume.getResumeTitle().toLowerCase().contains(search)) {
                    searchResult.add(resume);
                }
            }
            if(resume.getExpectedSalary() != null) {
                if(resume.getExpectedSalary().toString().contains(search)) {
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
        for (ResumeDto resume:
                r) {
            if(resume.getDateTime() == null) {
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
        for (ResumeDto resume:
                r ) {
            if(resume.getCategory() != null) {
                if(resume.getCategory().equalsIgnoreCase(category)) {
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
                    .id((long)i)
                    .title(l.get(i).getResumeTitle())
                    .dateTime(l.get(i).getDateTime())
                    .build());
        }
        return summaryDtos;
    }
}

