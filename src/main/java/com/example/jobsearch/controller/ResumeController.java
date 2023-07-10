package com.example.jobsearch.controller;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService service;
    @GetMapping
    public List<Resume> getResumes() {
        return service.getAllResumes();
    }
    @GetMapping("/{userId}")
    public List<Resume> getResumeByUserId(@PathVariable int userId) {
        return service.getResumesByUserId(userId);
    }
}
