package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerProfileService {
    private final ResumeService resumeService;
    private final JobApplicationService jobApplicationService;

    public List<ResumeDto> getAllResumesByVacancyId(long vacancyId) {
        List<Long> temp = jobApplicationService.getAllResumeIdsByVacancyId(vacancyId);
        return resumeService.getAllResumesByLongList(temp);
    }
}
