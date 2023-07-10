package com.example.jobsearch.controller;

import com.example.jobsearch.model.JobseekerResume;
import com.example.jobsearch.service.JobseekerResumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class JobseekerResumeController {
    private final JobseekerResumerService service;
    @GetMapping
    public List<JobseekerResume> getResumes() {
        return service.getAllJobseekerResumes();
    }
    @GetMapping("/{userId}")
    public List<JobseekerResume> getResumeByUserId(@PathVariable int userId) {
        return service.getJobseekerResumeByUserId(userId);
    }
}
