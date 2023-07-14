package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.model.ContactInfo;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public List<ResumeDto> getAllResumes() {
        List<Resume> list = resumeDao.getAllResumes();
        return list.stream()
                .map(this::makeDtoFromResume)
                .toList();
           }
    public List<ResumeDto> getAllResumesByUserId(long applicantId) {
        List<Resume> list = resumeDao.getAllResumesByUserId(applicantId);
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
    public void createResume(Resume e) {
        resumeDao.createResume(e);
    }
    public void deleteResume(Resume e) {
        resumeDao.deleteResume(e);
    }
    public void editResume(Resume e) {
        resumeDao.deleteResume(e);
    }
}
