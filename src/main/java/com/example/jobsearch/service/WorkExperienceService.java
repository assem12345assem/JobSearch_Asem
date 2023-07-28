package com.example.jobsearch.service;

import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceService {
    private final WorkExperienceDao workExperienceDao;

    public WorkExperienceDto getWorkExperienceById(long id) {
        WorkExperience workExperience = workExperienceDao.getWorkExperienceById(id);
        return makeDtoFromWorkExperience(workExperience);
    }

    public List<WorkExperienceDto> getAllWorkExperienceByResumeId(long resumeId) {
        List<WorkExperience> list = workExperienceDao.getAllWorkExperienceByResumeId(resumeId);

        return list.stream()
                .map(this::makeDtoFromWorkExperience)
                .toList();
    }

    private WorkExperienceDto makeDtoFromWorkExperience(WorkExperience workExperience) {
        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .resumeId(workExperience.getResumeId())
                .dateStart(workExperience.getDateStart())
                .dateEnd(workExperience.getDateEnd())
                .companyName(workExperience.getCompanyName())
                .position(workExperience.getPosition())
                .responsibilities(workExperience.getResponsibilities())
                .build();
    }

    private WorkExperience createWorkExpFromDto(WorkExperienceDto workExperience) {
        WorkExperience w = new WorkExperience();
        w.setId(workExperience.getId());
        w.setResumeId(workExperience.getResumeId());
        w.setDateStart(workExperience.getDateStart());
        w.setDateEnd(workExperience.getDateEnd());
        w.setCompanyName(workExperience.getCompanyName());
        w.setPosition(workExperience.getPosition());
        w.setResponsibilities(workExperience.getResponsibilities());
        return w;
    }

    public ResponseEntity<?> createWorkExperience(WorkExperienceDto e) {
        Long idTest = e.getResumeId();
        if (idTest == null) {
            log.warn("Tried to add work experience when resume does not exist");
            return new ResponseEntity<>("Cannot add work experience, resume does not exist", HttpStatus.NOT_FOUND);

        } else {
            workExperienceDao.save(createWorkExpFromDto(e));
            return new ResponseEntity<>("Work experience added successfully", HttpStatus.OK);

        }
    }

    public ResponseEntity<?> deleteWorkExperience(WorkExperienceDto e) {
        var we = workExperienceDao.findWorkExperienceById(e.getId());
        if (we.isPresent()) {
            workExperienceDao.delete(e.getId());
            return new ResponseEntity<>("Work experience entry was deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Work experience entry does not exist", HttpStatus.OK);

        }
    }

    public ResponseEntity<?> editWorkExperience(WorkExperienceDto e) {
        Long x = e.getId();
        if (x == null) {
            return new ResponseEntity<>("Work experience id is not valid", HttpStatus.NOT_FOUND);
        } else {
            workExperienceDao.editWorkExperience(createWorkExpFromDto(e));

            return new ResponseEntity<>("Work experience was edited successfully", HttpStatus.OK);

        }
    }
}
