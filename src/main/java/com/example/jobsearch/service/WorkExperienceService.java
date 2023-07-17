package com.example.jobsearch.service;

import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.model.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return WorkExperience.builder()
                .id(workExperience.getId())
                .resumeId(workExperience.getResumeId())
                .dateStart(workExperience.getDateStart())
                .dateEnd(workExperience.getDateEnd())
                .companyName(workExperience.getCompanyName())
                .position(workExperience.getPosition())
                .responsibilities(workExperience.getResponsibilities())
                .build();
    }
    public void createWorkExperience(WorkExperienceDto e) {
        workExperienceDao.createWorkExperience(createWorkExpFromDto(e));
    }
    public void deleteWorkExperience (WorkExperienceDto e) {
        workExperienceDao.deleteWorkExperience(createWorkExpFromDto(e));
    }

    public void editWorkExperience(WorkExperienceDto e) {
        workExperienceDao.editWorkExperience(createWorkExpFromDto(e));
    }
}
