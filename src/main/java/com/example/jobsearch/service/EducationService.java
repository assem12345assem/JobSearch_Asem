package com.example.jobsearch.service;

import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.model.Education;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationDao educationDao;
    public EducationDto getEducationById(long id) {
        Education education = educationDao.getEducationById(id);
        return makeDtoFromEducation(education);
    }
    public List<EducationDto> getAllEducationByResumeId(long resumeId) {
        List<Education> list = educationDao.getAllEducationByResumeId(resumeId);
        return list.stream()
                .map(this::makeDtoFromEducation)
                .toList();
    }

    private EducationDto makeDtoFromEducation(Education education) {
        EducationDto e = new EducationDto();
        e.setId(education.getId());
        e.setResumeId(education.getResumeId());
        e.setEducation(education.getEducation());
        e.setSchoolName(education.getSchoolName());
        e.setStartDate(education.getStartDate());
        e.setGraduationDate(education.getGraduationDate());
        return e;
    }

    private Education createEducationFromDto(EducationDto education) {
        Education e = new Education();
        e.setId(education.getId());
        e.setResumeId(education.getResumeId());
        e.setEducation(education.getEducation());
        e.setSchoolName(education.getSchoolName());
        e.setStartDate(education.getStartDate());
        e.setGraduationDate(education.getGraduationDate());
        return e;
    }

    public void createEducation(EducationDto e) {
        log.warn("Added new education to resume: {}", e.getResumeId());
        educationDao.createEducation(createEducationFromDto(e));
    }
    public void deleteEducation(EducationDto e) {
        educationDao.deleteEducation(createEducationFromDto(e));
    }
    public void editEducation(EducationDto e) {
        educationDao.editEducation(createEducationFromDto(e));
    }

}
