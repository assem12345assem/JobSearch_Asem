package com.example.jobsearch.service;


import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeDao resumeDao;
    private final EducationService educationService;
    private final WorkExperienceService workExperienceService;
    private final ContactInfoService contactInfoService;
    private final ApplicantService applicantService;
    private final CategoryService categoryService;
    private ResumeDto makeDtoFromResume(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicant(applicantService.getApplicantById(resume.getApplicantId()))
                .resumeTitle(resume.getResumeTitle())
                .category(categoryService.getCategoryById(resume.getCategoryId()))
                .expectedSalary(resume.getExpectedSalary())
                .isActive(resume.isActive())
                .isPublished(resume.isPublished())
                .contactInfo(contactInfoService.getAllContactInfoByResumeId(resume.getId()))
                .educationList(educationService.getAllEducationByResumeId(resume.getId()))
                .workExperienceList(workExperienceService.getAllWorkExperienceByResumeId(resume.getId()))
                .build();
    }
    private Resume createResumeFromDto( ResumeDto resume) {
        return Resume.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicant().getId())
                .resumeTitle(resume.getResumeTitle())
                .categoryId(resume.getCategory().getId())
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
                .toList(); }
    public void createResume(ResumeDto e) {
        resumeDao.createResume(createResumeFromDto(e));

    }
    public void deleteResume(ResumeDto e) {
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
}
