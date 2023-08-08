package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeMVCController {
    private final ResumeService resumeService;
    @GetMapping
    public String viewAllResumes(Model model) {
        model.addAttribute("resumes", resumeService.getAllResumes());
        return "resumes/all";
    }
    @GetMapping("/{resumeId}")
    public String getResume(@PathVariable Long resumeId, Model model, Model model2, Model model3, Model model4) {
        model.addAttribute("resume", resumeService.getResumeById(resumeId));
        model2.addAttribute("educationList", resumeService.getAllEducationByResumeId(resumeId));
        model3.addAttribute("workList", resumeService.getAllWorkExperienceByResumeId(resumeId));
        model4.addAttribute("contactInfo", resumeService.getAllContactInfoByResumeId(resumeId));
        return "resumes/resume_info";
    }

    @GetMapping("add_resume/{userId}")
    public String add(@PathVariable String userId) {
        return "resumes/add";
    }

    @PostMapping("add_resume/{userId}")
    public String add(@PathVariable String userId, ResumeDto resumeDto, WorkExperienceDto workExperienceDto, EducationDto educationDto, ContactInfoDto contactInfoDto) {
        resumeService.create(userId, resumeDto, workExperienceDto, educationDto, contactInfoDto);
        return "all";
    }
}
