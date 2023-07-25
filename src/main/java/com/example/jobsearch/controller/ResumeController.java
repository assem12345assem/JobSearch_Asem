package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.EducationDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.WorkExperienceDto;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/applicant/create")
    public HttpStatus createResume(ResumeDto resumeDto, Authentication auth) {
        resumeService.createResume(resumeDto, auth);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/edit")
    public HttpStatus editResume(ResumeDto resumeDto) {
        resumeService.editResume(resumeDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/applicant/delete")
    public HttpStatus deleteResume(ResumeDto resumeDto) {
        resumeService.deleteResume(resumeDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/add_education")
    public HttpStatus addEducation(EducationDto educationDto) {
        resumeService.addEducation(educationDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/add_work_experience")
    public HttpStatus addWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.addWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/add_contact_info")
    public HttpStatus addContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.addContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/edit_education")
    public HttpStatus editEducation(EducationDto educationDto) {
        resumeService.editEducation(educationDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/edit_work_experience")
    public HttpStatus editWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.editWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }

    @PostMapping("/applicant/edit_contact_info")
    public HttpStatus editContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.editContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/applicant/delete_education")
    public HttpStatus deleteEducation(EducationDto educationDto) {
        resumeService.deleteEducation(educationDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/applicant/delete_work_experience")
    public HttpStatus deleteWorkExperience(WorkExperienceDto workExperienceDto) {
        resumeService.deleteWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/applicant/delete_contact_info")
    public HttpStatus deleteContactInfo(ContactInfoDto contactInfoDto) {
        resumeService.deleteContactInfo(contactInfoDto);
        return HttpStatus.OK;
    }

    @GetMapping("/view")
    public List<ResumeDto> viewAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping("/all_work_experience/{resumeId}")
    public List<WorkExperienceDto> getAllWorkExperienceByResumeId(@PathVariable long resumeId) {
        return resumeService.getAllWorkExperienceByResumeId(resumeId);
    }

    @GetMapping("/all_education/{resumeId}")
    public List<EducationDto> getAllEducationByResumeId(@PathVariable long resumeId) {
        return resumeService.getAllEducationByResumeId(resumeId);
    }

    @GetMapping("/all_contact_info/{resumeId}")
    public ContactInfoDto getAllContactInfoByResumeId(@PathVariable long resumeId) {
        return resumeService.getAllContactInfoByResumeId(resumeId);
    }

    @GetMapping("/view/by_title/{title}")
    public List<ResumeDto> getResumeByResumeTitle(@PathVariable String title) {
        return resumeService.getResumeByResumeTitle(title);
    }

    @GetMapping("/view/by_category/{category}")
    public List<ResumeDto> getAllResumesByCategory(@PathVariable String category) {
        return resumeService.getAllResumesByCategory(category);
    }


    @GetMapping("/work_experience/{id}")
    public WorkExperienceDto getWorkExperienceById(@PathVariable long id) {
        return resumeService.getWorkExperienceById(id);
    }

    @GetMapping("/education/{id}")
    public EducationDto getEducationById(@PathVariable long id) {
        return resumeService.getEducationById(id);
    }
}
