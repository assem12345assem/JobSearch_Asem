package com.example.jobsearch.service;

import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        WorkExperienceDto w = new WorkExperienceDto();
        w.setId(workExperience.getId());
        w.setResumeId(workExperience.getResumeId());
        w.setDateStart(workExperience.getDateStart());
        w.setDateEnd(workExperience.getDateEnd());
        w.setCompanyName(workExperience.getCompanyName());
        w.setPosition(workExperience.getPosition());
        w.setResponsibilities(workExperience.getResponsibilities());
        return w;
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

    public void createWorkExperience(WorkExperienceDto e) {
        log.warn("Work experience was added: {}", e.getResumeId());
        workExperienceDao.save(createWorkExpFromDto(e));
    }

    public void deleteWorkExperience(WorkExperienceDto e) {
        workExperienceDao.delete(e.getId());
    }

    public void editWorkExperience(WorkExperienceDto e) {
        workExperienceDao.editWorkExperience(createWorkExpFromDto(e));
    }
}
