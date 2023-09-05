package com.example.jobsearch.service;


import com.example.jobsearch.dto.*;
import com.example.jobsearch.entity.JobApplication;
import com.example.jobsearch.entity.Message;
import com.example.jobsearch.entity.Vacancy;
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
        messageRepository.save(Message.builder()
                .jobApplication(j)
                .message(firstJobApplicationDto.getMessageText())
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
        return jobApplicationRepository.findById(jobApplicationId).orElseThrow(() -> new NoSuchElementException("Job application not found"));
    }

    public List<MessageDto> getMessages(Long jobApplicationId, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
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
            List<Vacancy> va = vacancyService.findAllByEmployer(auth);
            if (va != null) {
                for (Vacancy v :
                        va) {
                    j.addAll(jobApplicationRepository.findAllByVacancyId(v.getId()));
                }
            }
        }

        List<MessageListDto> list = new ArrayList<>();
        for (JobApplication jobApplication :
                j) {
            List<Message> msg = messageRepository.findAllByJobApplicationId(jobApplication.getId());

            VacancyDto vDto = vacancyService.findDtoById(jobApplication.getVacancy().getId());
            ResumeDto rDto = resumeService.findDtoById(jobApplication.getResume().getId());
            list.add(MessageListDto.builder()
                    .jobApplicationId(jobApplication.getId())
                    .vacancyName(vDto.getVacancyName())
                    .publisher(vDto.getProfile().getCompanyName())
                    .applicant(rDto.getProfile().getFirstName() + " " + rDto.getProfile().getLastName())
                    .newMessage(msg.size())
                    .build());
        }
        return list;
    }

    public Integer getNew(Authentication auth) {
        List<MessageListDto> temp = listMessages(auth);
        int counter = 0;
        for (MessageListDto m :
                temp) {
            if (m.getNewMessage() > 0) {
                counter += m.getNewMessage();
            }
        }
        return counter;
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
        if(j.isEmpty()) {
            j.add(0);
        }
        return j;
    }
}
