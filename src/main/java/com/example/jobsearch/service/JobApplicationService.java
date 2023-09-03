package com.example.jobsearch.service;


import com.example.jobsearch.dao.JobApplicationDao;
import com.example.jobsearch.dao.MessageDao;
import com.example.jobsearch.dto.*;
import com.example.jobsearch.model.JobApplication;
import com.example.jobsearch.model.Message;
import com.example.jobsearch.model.Vacancy;
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
    private final JobApplicationDao jobApplicationDao;
    private final MessageDao messageDao;
    private final AuthService authService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    public Long save(FirstJobApplicationDto firstJobApplicationDto, Authentication auth, String type) {
        UserDto u = authService.getAuthor(auth);
      Long id =  jobApplicationDao.save(JobApplication.builder()
                        .vacancyId(firstJobApplicationDto.getVacancyId())
                        .resumeId(firstJobApplicationDto.getResumeId())
                        .dateTime(LocalDateTime.now())
                .build());
      messageDao.save(Message.builder()
                      .jobApplicationId(id)
                      .message(type + firstJobApplicationDto.getResumeId())
                      .author(u.getEmail())
              .createTime(LocalDateTime.now())
              .build());
         messageDao.save(Message.builder()
                        .jobApplicationId(id)
                        .message(firstJobApplicationDto.getMessageText())
                        .author(u.getEmail())
                         .createTime(LocalDateTime.now())
                .build());
         return id;
    }

    public VacancyDto findVacancyDtoById(Long jobApplicationId) {
        JobApplication j = findById(jobApplicationId);
        return vacancyService.findDtoById(j.getVacancyId());
    }
    public JobApplication findById(Long jobApplicationId) {
        return jobApplicationDao.find(jobApplicationId).orElseThrow(() -> new NoSuchElementException("Job application not found"));
    }

    public List<MessageDto> getMessages(Long jobApplicationId, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        List<Message> list = messageDao.getAllByJobApplication(jobApplicationId);

        return list.stream().map(this::makeDtoFromMessage).toList();
    }
    private MessageDto makeDtoFromMessage(Message m) {
        return MessageDto.builder()
                .id(m.getId())
                .jobApplicationId(m.getJobApplicationId())
                .message(m.getMessage())
                .author(m.getAuthor())
                .createTime(m.getCreateTime().toString())
                .build();
    }
    private JobApplicationDto makeDtoFromJobApplication(JobApplication j) {
        return JobApplicationDto.builder()
                .id(j.getId())
                .vacancyId(j.getVacancyId())
                .resumeId(j.getResumeId())
                .dateTime(j.getDateTime())
                .build();
    }

    public List<MessageListDto> listMessages(Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        List<JobApplication> j = new ArrayList<>();
        if(u.getUserType().equalsIgnoreCase("applicant")) {
            List<ResumeDto> re = resumeService.findAllByApplicant(auth);
            if(re != null) {
                for (ResumeDto r:
                     re) {
                    j.addAll(jobApplicationDao.findByResumeId(r.getId()));
                }
            }
        } else {
            List<Vacancy> va = vacancyService.findAllByEmployer(auth);
            if(va != null) {
                for (Vacancy v:
                     va) {
                    j.addAll(jobApplicationDao.findByVacancyId(v.getId()));
                }
            }
        }

        List<MessageListDto> list = new ArrayList<>();
        for (JobApplication jobApplication:
             j) {
            List<Message> msg = messageDao.getAllByJobApplication(jobApplication.getId());

            VacancyDto vDto = vacancyService.findDtoById(jobApplication.getVacancyId());
            ResumeDto rDto = resumeService.findDtoById(jobApplication.getResumeId());
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
        for (MessageListDto m:
             temp) {
            if(m.getNewMessage() > 0) {
                counter += m.getNewMessage();
            }
        }
        return counter;
    }

    public void sendMessage(MessageDto messageDto, Authentication auth) {
        UserDto u = authService.getAuthor(auth);
        messageDao.save(Message.builder()
                        .jobApplicationId(messageDto.getJobApplicationId())
                        .message(messageDto.getMessage())
                        .author(u.getEmail())
                        .createTime(LocalDateTime.now())
                .build());
    }

    public ResumeDto findResumeDtoById(Long jobApplicationId) {
        JobApplication j = findById(jobApplicationId);
        return resumeService.findDtoById(j.getResumeId());
    }
}
