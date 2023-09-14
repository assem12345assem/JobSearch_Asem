package com.example.jobsearch.service;

import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.entity.Education;
import com.example.jobsearch.entity.Resume;
import com.example.jobsearch.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    public List<EducationDto> getDtoListByResumeId(Long resumeId) {
        List<EducationDto> e = educationRepository.findByResumeId(resumeId).stream()
                .map(education -> EducationDto.builder()
                        .id(education.getId())
                        .education(education.getEducation())
                        .schoolName(education.getSchoolName())
                        .startDate(education.getStartDate())
                        .graduationDate(education.getGraduationDate())
                        .build())
                .collect(Collectors.toList());
        return e;
    }

    public void saveEducation(EducationDto educationDto, Resume resume) {
        educationRepository.save(Education.builder()
                .resume(resume)
                .education(educationDto.getEducation())
                .schoolName(educationDto.getSchoolName())
                .startDate(educationDto.getStartDate())
                .graduationDate(educationDto.getGraduationDate())
                .build());
    }
    public Long deleteEducation(Long educationId) {
        Education e = educationRepository.findById(educationId).orElseThrow(() -> {
            log.warn("Education not found: {}", educationId);
            return new NoSuchElementException("Education not found");
        });
        educationRepository.delete(e);
        return e.getResume().getId();
    }

}
