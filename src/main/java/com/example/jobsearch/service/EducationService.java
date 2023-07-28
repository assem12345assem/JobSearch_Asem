package com.example.jobsearch.service;

import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.model.Education;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> createEducation(EducationDto e) {
        Long idTest = e.getResumeId();
        if(idTest == null) {
            log.warn("Tried to add education when resume does not exist");
            return new ResponseEntity<>("Cannot add education, resume does not exist", HttpStatus.NOT_FOUND);
        } else {
            educationDao.save(createEducationFromDto(e));
            return new ResponseEntity<>("Education added successfully", HttpStatus.OK);
        }
    }
    public ResponseEntity<?>  deleteEducation(EducationDto e) {
        var edu = educationDao.findEducationById(e.getId());
        if(edu.isPresent()) {
            educationDao.delete(e.getId());
            return new ResponseEntity<>("Education entry was deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Education entry does not exist", HttpStatus.OK);

        }
    }
    public ResponseEntity<?> editEducation(EducationDto e) {
        Long x = e.getId();
        if(x == null) {
            return new ResponseEntity<>("Education entry id is not valid", HttpStatus.NOT_FOUND);
        } else {
            educationDao.editEducation(createEducationFromDto(e));
            return new ResponseEntity<>("Education was edited successfully", HttpStatus.OK);

        }
    }

}
