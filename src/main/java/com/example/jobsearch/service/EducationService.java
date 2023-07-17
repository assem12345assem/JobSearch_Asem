package com.example.jobsearch.service;

import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.model.Education;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return EducationDto.builder()
                .id(education.getId())
                .resumeId(education.getResumeId())
                .education(education.getEducation())
                .schoolName(education.getSchoolName())
                .startDate(education.getStartDate())
                .graduationDate(education.getGraduationDate())
                .build();
    }
    private Education createEducationFromDto(EducationDto education) {
        return Education.builder()
                .id(education.getId())
                .resumeId(education.getResumeId())
                .education(education.getEducation())
                .schoolName(education.getSchoolName())
                .startDate(education.getStartDate())
                .graduationDate(education.getGraduationDate())
                .build();
    }

    public void createEducation(EducationDto e) {
        educationDao.createEducation(createEducationFromDto(e));
    }
    public void deleteEducation (EducationDto e) {
        educationDao.deleteEducation(createEducationFromDto(e));
    }

    public void editEducation(EducationDto e) {
        educationDao.editEducation(createEducationFromDto(e));
    }

}
