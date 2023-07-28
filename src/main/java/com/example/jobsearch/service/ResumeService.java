package com.example.jobsearch.service;


import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeDao resumeDao;
    private final EducationService educationService;
    private final WorkExperienceService workExperienceService;
    private final ContactInfoService contactInfoService;
    private final ApplicantProfileService applicantService;
    private final UserService userService;

    private ResumeDto makeDtoFromResume(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .authorEmail(applicantService.getApplicantById(resume.getApplicantId()).getUserId())
                .resumeTitle(resume.getResumeTitle())
                .category(resume.getCategory())
                .expectedSalary(resume.getExpectedSalary())
                .isActive(resume.isActive())
                .isPublished(resume.isPublished())
                .build();
    }

    private Resume createResumeFromDto(ResumeDto resume) {
        return Resume.builder()
                .id(resume.getId())
                .applicantId(applicantService.findApplicantByUserId(resume.getAuthorEmail()).getId())
                .resumeTitle(resume.getResumeTitle())
                .category(resume.getCategory())
                .expectedSalary(resume.getExpectedSalary())
                .isActive(resume.isActive())
                .isPublished(resume.isPublished())
                .build();
    }

    public List<ResumeDto> getAllResumes() {
        List<Resume> list = resumeDao.getAllResumes();
        return list.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }


    public List<ResumeDto> getAllResumesByApplicantId(long applicantId) {
        List<Resume> list = resumeDao.getAllResumesByApplicantId(applicantId);
        return list.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public List<ResumeDto> getAllResumesByCategory(String categoryId) {
        List<Resume> list = resumeDao.getAllResumesByCategory(categoryId);
        return list.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public List<ResumeDto> getAllResumesByCategoryName(String categoryName) {
        List<Resume> list = resumeDao.getAllResumesByCategoryName(categoryName);
        return list.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public ResponseEntity<?> createResume(ResumeDto e, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(user.getId().equalsIgnoreCase(e.getAuthorEmail())) {
            Optional<Resume> r;
            if(e.getId() == null) {
            long x = (long)resumeDao.getAllResumes().size()+1;
            r = resumeDao.getResumeById(x);
            } else {
                r = resumeDao.getResumeById(e.getId());
            }
            if(r.isEmpty()) {
                resumeDao.save(createResumeFromDto(e));
                return new ResponseEntity<>("Resume created successfully", HttpStatus.OK);
            } else {
                log.info("Tried to create a resume that already exists: {}", e.getId());
                return new ResponseEntity<>("Resume already exists", HttpStatus.OK);
            }
        } else {
            log.warn("Tried to create a resume for another user: {}", user.getId());
            return new ResponseEntity<>("Tried to create a resume for another user", HttpStatus.BAD_REQUEST);
        }
    }
    private ResponseEntity<?> handleResumeQueries(Optional<Resume> maybeResume) {
        if(maybeResume.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeDtoFromResume(maybeResume.get()), HttpStatus.OK);
    }
    public ResponseEntity<?> deleteResume(ResumeDto e, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(user.getId().equalsIgnoreCase(e.getAuthorEmail())) {
            var r = resumeDao.getResumeById(e.getId());
            if (r != null) {
                resumeDao.delete(e.getId());
                return new ResponseEntity<>("Resume deleted successfully", HttpStatus.OK);
            } else {
                log.info("Tried to delete a resume that does not exist: {}", e.getId());
                return new ResponseEntity<>("Tried to delete a resume that does not exist", HttpStatus.OK);
            }
        } else {
            log.warn("Tried to delete a resume of another user: {}", user.getId());
            return new ResponseEntity<>("Tried to delete a resume of another user", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> editResume(ResumeDto e, Authentication auth) {
        var u = auth.getPrincipal();
        User user = userService.getUserFromAuth(u.toString());
        if(user.getId().equalsIgnoreCase(e.getAuthorEmail())) {
            var r = resumeDao.getResumeById(e.getId());
            if (r != null) {
                resumeDao.editResume(createResumeFromDto(e));
            return new ResponseEntity<>("Resume edited successfully", HttpStatus.OK);
        } else {
                log.warn("Tried to edit a resume that does not exist: {}", e.getId());
                return new ResponseEntity<>("Cannot edit a resume that does not exist", HttpStatus.NOT_FOUND);
            }
        } else {
            log.warn("Tried to edit a resume of another user: {}", user.getId());
            return new ResponseEntity<>("Tried to edit a resume of another user", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ResumeDto> getAllResumesByLongList(List<Long> list) {
        List<Resume> listR = resumeDao.getAllResumesByLongList(list);
        return listR.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public ResumeDto getResumeById(long id) {
        return makeDtoFromResume(resumeDao.findResumeById(id));
    }

    public List<ResumeDto> getResumeByResumeTitle(String title) {
        List<Resume> resumes = resumeDao.getResumeByResumeTitle(title);
        return resumes.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public void addEducation(EducationDto educationDto) {
        educationService.createEducation(educationDto);

    }

    public void editEducation(EducationDto educationDto) {
        educationService.editEducation(educationDto);
    }

    public void deleteEducation(EducationDto educationDto) {
        educationService.deleteEducation(educationDto);
    }

    public void addWorkExperience(WorkExperienceDto workExperienceDto) {
        workExperienceService.createWorkExperience(workExperienceDto);
    }

    public void editWorkExperience(WorkExperienceDto workExperienceDto) {
        workExperienceService.editWorkExperience(workExperienceDto);
    }

    public void deleteWorkExperience(WorkExperienceDto workExperienceDto) {
        workExperienceService.deleteWorkExperience(workExperienceDto);
    }

    public void addContactInfo(ContactInfoDto contactInfoDto) {
        contactInfoService.createContactInfo(contactInfoDto);
    }

    public void editContactInfo(ContactInfoDto contactInfoDto) {
        contactInfoService.editContactInfo(contactInfoDto);
    }

    public void deleteContactInfo(ContactInfoDto contactInfoDto) {
        contactInfoService.deleteContactInfo(contactInfoDto);
    }
    public List<WorkExperienceDto> getAllWorkExperienceByResumeId(long resumeId) {
        return workExperienceService.getAllWorkExperienceByResumeId((resumeId));
    }

    public WorkExperienceDto getWorkExperienceById(long id) {
        return workExperienceService.getWorkExperienceById(id);
    }

    public EducationDto getEducationById(Long educationId) {
        return educationService.getEducationById(educationId);
    }

    public List<EducationDto> getAllEducationByResumeId(long resumeId) {
        return educationService.getAllEducationByResumeId(resumeId);
    }

    public ContactInfoDto getAllContactInfoByResumeId(long resumeId) {
        return contactInfoService.getAllContactInfoByResumeId(resumeId);
    }
}
