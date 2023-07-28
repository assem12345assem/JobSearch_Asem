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

    private EducationDto makeDtoFromEducation(Education e) {
        return EducationDto.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .education(e.getEducation())
                .schoolName(e.getSchoolName())
                .startDate(e.getStartDate())
                .graduationDate(e.getGraduationDate())
                .build();
    }

    private Education createEducationFromDto(EducationDto e) {
        return Education.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .education(e.getEducation())
                .schoolName(e.getSchoolName())
                .startDate(e.getStartDate())
                .graduationDate(e.getGraduationDate())
                .build();
    }

    public void createEducation(EducationDto e) {
        log.info("Added new education to resume: {}", e.getResumeId());
        educationDao.save(createEducationFromDto(e));
    }
    public void deleteEducation(EducationDto e) {
        educationDao.delete(e.getId());
    }
    public void editEducation(EducationDto e) {
        educationDao.editEducation(createEducationFromDto(e));
    }

}
