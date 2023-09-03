package com.example.jobsearch.controller;

import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping("/applicant/create")
    public ResponseEntity<?> createResume(@RequestBody ResumeDto resumeDto, Authentication auth) {
        return resumeService.createResume(resumeDto, auth);
    }
    @PostMapping("/applicant/edit")
    public ResponseEntity<?> editResume(ResumeDto resumeDto, Authentication auth) {
        return resumeService.editResume(resumeDto, auth);
    }

    @DeleteMapping("/applicant/delete")
    public ResponseEntity<?> deleteResume(ResumeDto resumeDto, Authentication auth) {
        return resumeService.deleteResume(resumeDto, auth);
    }

    @PostMapping("/applicant/add_education")
    public ResponseEntity<?> addEducation(EducationDto educationDto) {
       return resumeService.addEducation(educationDto);
    }

    @PostMapping("/applicant/add_work_experience")
    public ResponseEntity<?> addWorkExperience(WorkExperienceDto workExperienceDto) {
        return resumeService.addWorkExperience(workExperienceDto);
    }

    @PostMapping("/applicant/add_contact_info")
    public ResponseEntity<?> addContactInfo(ContactInfoDto contactInfoDto) {
        return resumeService.addContactInfo(contactInfoDto);
    }

    @PostMapping("/applicant/edit_education")
    public ResponseEntity<?> editEducation(EducationDto educationDto) {
        return resumeService.editEducation(educationDto);
    }

    @PostMapping("/applicant/edit_work_experience")
    public ResponseEntity<?>  editWorkExperience(WorkExperienceDto workExperienceDto) {
        return resumeService.editWorkExperience(workExperienceDto);
    }

    @PostMapping("/applicant/edit_contact_info")
    public ResponseEntity<?> editContactInfo(ContactInfoDto contactInfoDto) {
        return resumeService.editContactInfo(contactInfoDto);
    }

    @DeleteMapping("/applicant/delete_education")
    public ResponseEntity<?>  deleteEducation(EducationDto educationDto) {
        return resumeService.deleteEducation(educationDto);
    }

    @DeleteMapping("/applicant/delete_work_experience")
    public ResponseEntity<?> deleteWorkExperience(WorkExperienceDto workExperienceDto) {
        return resumeService.deleteWorkExperience(workExperienceDto);
    }

    @DeleteMapping("/applicant/delete_contact_info")
    public ResponseEntity<?> deleteContactInfo(ContactInfoDto contactInfoDto) {
       return resumeService.deleteContactInfo(contactInfoDto);
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
