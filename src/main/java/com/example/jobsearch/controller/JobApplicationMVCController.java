package com.example.jobsearch.controller;

import com.example.jobsearch.dto.FirstJobApplicationDto;
import com.example.jobsearch.dto.MessageDto;
import com.example.jobsearch.service.AuthService;
import com.example.jobsearch.service.JobApplicationService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apply")
@RequiredArgsConstructor
public class JobApplicationMVCController {
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final AuthService authService;
    private final JobApplicationService jobApplicationService;

    @GetMapping("/forjob/{vacancyId}")
    public String apply(@PathVariable Long vacancyId, Model model, Authentication auth) {
        model.addAttribute("vacancy", vacancyService.findDtoById(vacancyId));
        model.addAttribute("user", vacancyService.getVacancyOwner(vacancyId));
        model.addAttribute("resumes", resumeService.findAllByApplicant(auth));
        return "message/start";
    }
    @PostMapping("/forjob/{vacancyId}")
    public String apply(@PathVariable Long vacancyId, @ModelAttribute FirstJobApplicationDto firstJobApplicationDto, Authentication auth) {
        Long jobApplicationId = jobApplicationService.save(firstJobApplicationDto, auth, "/resume/info/");
        return "redirect:/apply/chat/" + jobApplicationId;
    }
    @GetMapping("/offerjob/{resumeId}")
    public String offerJob(@PathVariable Long resumeId, Model model, Authentication auth) {
        model.addAttribute("vacancies", vacancyService.findAllByEmployer(auth));
        model.addAttribute("user", resumeService.getResumeOwner(resumeId));
        model.addAttribute("resume", resumeService.findDtoById(resumeId));
        return "message/start_employer";
    }
    @PostMapping("/offerjob/{resumeId}")
    public String offerJob(@PathVariable Long resumeId, @ModelAttribute FirstJobApplicationDto firstJobApplicationDto, Authentication auth) {
        Long jobApplicationId = jobApplicationService.save(firstJobApplicationDto, auth, "/vacancy/");
        return "redirect:/apply/chat/" + jobApplicationId;
    }
    @GetMapping("/all/chat/{jobApplicationId}")
    public String chat(@PathVariable Long jobApplicationId, Model model, Authentication auth) {
        model.addAttribute("user", authService.getAuthor(auth));
        model.addAttribute("vacancy", jobApplicationService.findVacancyDtoById(jobApplicationId));
        model.addAttribute("resume", jobApplicationService.findResumeDtoById(jobApplicationId));
        model.addAttribute("messages", jobApplicationService.getMessages(jobApplicationId, auth));
        return "message/continue";
    }
    @GetMapping("/all/messages")
    public String messagesList(Model model, Authentication auth) {
        model.addAttribute("chatList", jobApplicationService.listMessages(auth));
        return "message/messages";
    }
    @PostMapping("/all/send_message")
    public String sendMessage(@RequestBody MessageDto messageDto, Authentication auth) {
        jobApplicationService.sendMessage(messageDto, auth);
        return "redirect:/apply/chat/" + messageDto.getJobApplicationId();
    }
}
