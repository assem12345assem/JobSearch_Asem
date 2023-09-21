package com.example.jobsearch.service;


import com.example.jobsearch.dto.*;
import com.example.jobsearch.entity.JobApplication;
import com.example.jobsearch.entity.Message;
import com.example.jobsearch.repository.JobApplicationRepository;
import com.example.jobsearch.repository.MessageRepository;
import com.example.jobsearch.repository.ResumeRepository;
import com.example.jobsearch.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final MessageRepository messageRepository;
    private final AuthService authService;
    private final VacancyService vacancyService;
    private final VacancyRepository vacancyRepository;
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;

    public Long save(FirstJobApplicationDto firstJobApplicationDto, Authentication auth, String type) {
        UserDto u = authService.getAuthor(auth);
        JobApplication j = jobApplicationRepository.save(JobApplication.builder()
                .vacancy(vacancyRepository.findById(firstJobApplicationDto.getVacancyId()).orElseThrow(() -> new NoSuchElementException("Vacancy not found")))
                .resume(resumeRepository.findById(firstJobApplicationDto.getResumeId()).orElseThrow(() -> new NoSuchElementException("Resume not found")))
                .dateTime(LocalDateTime.now())
                .build());
        messageRepository.save(Message.builder()
                .jobApplication(j)
                .message(type + firstJobApplicationDto.getResumeId())
                .author(u.getEmail())
                .createTime(LocalDateTime.now())
                .build());
        return j.getId();
    }

    public VacancyDto findVacancyDtoById(Long jobApplicationId) {
        JobApplication j = findById(jobApplicationId);
        return vacancyService.findDtoById(j.getVacancy().getId());
    }

    public JobApplication findById(Long jobApplicationId) {
        return jobApplicationRepository.findById(jobApplicationId).orElseThrow(() -> {
            log.warn("Job application was not found: {}", jobApplicationId);
            return new NoSuchElementException("Job application not found");
        });
    }

    public List<MessageDto> getMessages(Long jobApplicationId, Authentication auth) {
        List<Message> list = messageRepository.findAllByJobApplicationId(jobApplicationId);

        return list.stream().map(this::makeDtoFromMessage).toList();
    }

    private MessageDto makeDtoFromMessage(Message m) {
        return MessageDto.builder()
                .id(m.getId())
                .jobApplicationId(m.getJobApplication().getId())
                .message(m.getMessage())
                .author(m.getAuthor())
                .createTime(m.getCreateTime().toString())
                .build();
    }

    private JobApplicationDto makeDtoFromJobApplication(JobApplication j) {
        return JobApplicationDto.builder()
                .id(j.getId())
                .vacancyId(j.getVacancy().getId())
                .resumeId(j.getResume().getId())
                .dateTime(j.getDateTime())
                .build();
    }

    public List<MessageListDto> listMessages(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        List<JobApplication> j = new ArrayList<>();
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            List<ResumeDto> re = resumeService.findAllByApplicant(auth);
            if (re != null) {
                for (ResumeDto r :
                        re) {
                    j.addAll(jobApplicationRepository.findAllByResumeId(r.getId()));
                }
            }
        } else {
            List<JobApplication> va = vacancyService.findAllByEmployer(auth)
                    .stream()
                    .map(v -> jobApplicationRepository.findAllByVacancyId(v.getId()))
                    .flatMap(List::stream)
                    .toList();

        }

        List<MessageListDto> list = new ArrayList<>();
        for (JobApplication jobApplication :
                j) {
            List<Message> msg = messageRepository.findAllByJobApplicationId(jobApplication.getId());

            VacancyDto vDto = vacancyService.findDtoById(jobApplication.getVacancy().getId());
            ResumeDto rDto = resumeService.findDtoById(jobApplication.getResume().getId());
            String company = vDto.getProfile().getCompanyName() != null ? vDto.getProfile().getCompanyName() : "${springMacroRequestContext.getMessage('no.info')}";
            String firstName = rDto.getProfile().getFirstName();
            String lastName = rDto.getProfile().getLastName();
            String applicant = (firstName != null && lastName != null) ? (firstName + " " + lastName) : (firstName != null ? firstName : (lastName != null ? lastName : "${springMacroRequestContext.getMessage('no.info')}"));
            String vacancy = vDto.getVacancyName() != null ? vDto.getVacancyName() : "${springMacroRequestContext.getMessage('no.info')}";
            list.add(MessageListDto.builder()
                    .jobApplicationId(jobApplication.getId())
                    .vacancyName(vacancy)
                    .publisher(company)
                    .applicant(applicant)
                    .newMessage(msg.size())
                    .build());
        }
        return list;
    }

    public Integer getNew(Authentication auth) {
        return listMessages(auth)
                .stream()
                .mapToInt(MessageListDto::getNewMessage)
                .filter(newMessage -> newMessage > 0)
                .sum();
    }

    public void sendMessage(MessageDto messageDto, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        messageRepository.save(Message.builder()
                .jobApplication(findById(messageDto.getJobApplicationId()))
                .message(messageDto.getMessage())
                .author(u.getEmail())
                .createTime(LocalDateTime.now())
                .build());
    }

    public ResumeDto findResumeDtoById(Long jobApplicationId) {
        JobApplication j = findById(jobApplicationId);
        return resumeService.findDtoById(j.getResume().getId());
    }

    public List<Integer> getCountByVacancy() {
        var j = jobApplicationRepository.getCountByVacancy();
        if (j.isEmpty()) {
            j.add(0);
        }
        return j;
    }

}
