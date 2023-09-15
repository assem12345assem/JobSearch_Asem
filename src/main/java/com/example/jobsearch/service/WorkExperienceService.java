package com.example.jobsearch.service;

import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.entity.Resume;
import com.example.jobsearch.entity.WorkExperience;
import com.example.jobsearch.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;

    public List<WorkExperienceDto> getDtoListByResumeId(Long resumeId) {
        return workExperienceRepository.findByResumeId(resumeId).stream()
                .map(e -> WorkExperienceDto.builder()
                        .id(e.getId())
                        .dateStart(e.getDateStart())
                        .dateEnd(e.getDateEnd())
                        .companyName(e.getCompanyName())
                        .position(e.getPosition())
                        .responsibilities(e.getResponsibilities())
                        .build())
                .collect(Collectors.toList());
    }
    public void saveWorkExperience(WorkExperienceDto workExperienceDto, Resume resume) {
        workExperienceRepository.save(WorkExperience.builder()
                .resume(resume)
                .dateStart(workExperienceDto.getDateStart())
                .dateEnd(workExperienceDto.getDateEnd())
                .companyName(workExperienceDto.getCompanyName())
                .position(workExperienceDto.getPosition())
                .responsibilities(workExperienceDto.getResponsibilities())
                .build());
    }
    public Long deleteWorkExperience(Long workExperienceId) {
        WorkExperience w = workExperienceRepository.findById(workExperienceId).orElseThrow(() -> {
            log.warn("Work experience not found: {}", workExperienceId);
            return new NoSuchElementException("Work experience not found");
        });
        workExperienceRepository.delete(w);
        return w.getResume().getId();
    }
}
