package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping("/create_resume")
    public HttpStatus createResume(ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_resume")
    public HttpStatus editResume(ResumeDto resumeDto) {
        resumeService.editResume(resumeDto);
        return HttpStatus.OK;
    }
    @DeleteMapping("/delete_resume")
    public HttpStatus deleteResume(ResumeDto resumeDto) {
        resumeService.deleteResume(resumeDto);
        return HttpStatus.OK;
    }
    @PostMapping("/add_education")
    public HttpStatus addEducation(EducationDto educationDto) {
        resumeService.addEducation(educationDto);
        return HttpStatus.OK;
    }
    @PostMapping("/add_work_experience")
    public HttpStatus addWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.addWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }
    @PostMapping("/add_contact_info")
    public HttpStatus addContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.addContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_education")
    public HttpStatus editEducation(EducationDto educationDto) {
        resumeService.editEducation(educationDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_work_experience")
    public HttpStatus editWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.editWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_contact_info")
    public HttpStatus editContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.editContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }
    @PostMapping("/delete_education")
    public HttpStatus deleteEducation(EducationDto educationDto) {
        resumeService.deleteEducation(educationDto);
        return HttpStatus.OK;
    }
    @PostMapping("/delete_work_experience")
    public HttpStatus deleteWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.deleteWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }
    @PostMapping("/edit_contact_info")
    public HttpStatus deleteContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.deleteContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }
    @GetMapping("/view_all_resumes")
    public List<ResumeDto> viewAllResumes() {
        return resumeService.getAllResumes();
    }
    @GetMapping("/get_resume_by_title/{title}")
    public List<ResumeDto> getResumeByResumeTitle(@PathVariable String title) {
        return resumeService.getResumeByResumeTitle(title);
    }
    @GetMapping("/get_resume_by_category/{category}")
    public List<ResumeDto> getAllResumesByCategoryName(@PathVariable String category) {
        return resumeService.getAllResumesByCategoryName(category);
    }
    @GetMapping("/get_resume_by_category_id/{id}")
    public List<ResumeDto> getAllResumesByCategoryId(@PathVariable long id) {
        return resumeService.getAllResumesByCategoryId(id);
    }
    @GetMapping("/resumes_by_vacancy_id/{vacancyId}")
    public List<ResumeDto> getResumesByVacancyId(@PathVariable Long vacancyId) {
        return resumeService.getAllResumesByVacancyId(vacancyId);
    }

    @GetMapping("/work_experience/{id}")
    public WorkExperienceDto getWorkExperienceById(@PathVariable Long workExperienceId) {
        return resumeService.getWorkExperienceById(workExperienceId);
    }
    @GetMapping("/education/{id}")
    public EducationDto getEducationById(@PathVariable Long educationId) {
        return resumeService.getEducationById(educationId);
    }
}
