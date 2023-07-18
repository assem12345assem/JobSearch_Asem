package com.example.jobsearch.service;


import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeDao resumeDao;
    private final EducationService educationService;
    private final WorkExperienceService workExperienceService;
    private final ContactInfoService contactInfoService;
    private final ApplicantProfileService applicantService;
    private final CategoryService categoryService;

    private ResumeDto makeDtoFromResume(Resume resume) {
        ResumeDto r = new ResumeDto();
        r.setId(resume.getId());
        r.setApplicant(applicantService.getApplicantById(resume.getApplicantId()));
        r.setResumeTitle(resume.getResumeTitle());
        r.setCategory(categoryService.getCategoryById(resume.getCategoryId()));
        r.setExpectedSalary(resume.getExpectedSalary());
        r.setActive(resume.isActive());
        r.setPublished(resume.isPublished());
        return r;
    }

    private Resume createResumeFromDto(ResumeDto resume) {
        Resume r = new Resume();
        r.setId(resume.getId());
        r.setApplicantId(resume.getApplicant().getId());
        r.setResumeTitle(resume.getResumeTitle());
        r.setCategoryId(resume.getCategory().getId());
        r.setExpectedSalary(resume.getExpectedSalary());
        r.setActive(resume.isActive());
        r.setPublished(resume.isPublished());
        return r;
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

    public List<ResumeDto> getAllResumesByCategoryId(long categoryId) {
        List<Resume> list = resumeDao.getAllResumesByCategoryId(categoryId);
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

    public void createResume(ResumeDto e) {
        log.warn("New resume created: {}", e.getId());
        resumeDao.createResume(createResumeFromDto(e));

    }

    public void deleteResume(ResumeDto e) {
        log.warn("Resume deleted: {}", e.getId());
        resumeDao.deleteResume(createResumeFromDto(e));
        List<WorkExperienceDto> w = e.getWorkExperienceList();
        List<EducationDto> ed = e.getEducationList();
        ContactInfoDto ci = e.getContactInfo();
        w.forEach(workExperienceService::deleteWorkExperience);
        ed.forEach(educationService::deleteEducation);
        contactInfoService.deleteContactInfo(ci);
    }

    public void editResume(ResumeDto e) {
        resumeDao.editResume(createResumeFromDto(e));
    }

    public List<ResumeDto> getAllResumesByLongList(List<Long> list) {
        List<Resume> listR = resumeDao.getAllResumesByLongList(list);
        return listR.stream()
                .map(this::makeDtoFromResume)
                .toList();
    }

    public ResumeDto getResumeById(long id) {
        return makeDtoFromResume(resumeDao.getResumeById(id));
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
