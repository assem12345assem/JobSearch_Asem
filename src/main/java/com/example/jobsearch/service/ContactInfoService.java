package com.example.jobsearch.service;

import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.entity.ContactInfo;
import com.example.jobsearch.entity.Resume;
import com.example.jobsearch.repository.ContactInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoDto getDtoByResumeId(Long resumeId) {
        var contactInfoVar = contactInfoRepository.findByResumeId(resumeId);
        if (contactInfoVar.isEmpty()) return null;
        else {
            ContactInfo contactInfo = contactInfoVar.get();
            return ContactInfoDto.builder()
                    .telegram(contactInfo.getTelegram())
                    .email(contactInfo.getEmail())
                    .phoneNumber(contactInfo.getPhoneNumber())
                    .facebook(contactInfo.getFacebook())
                    .linkedIn(contactInfo.getLinkedIn())
                    .build();
        }
    }
public void saveContactInfo(ResumeDto resumeDto, Resume r) {
    var contactInfoVar = contactInfoRepository.findByResumeId(r.getId());
    if (contactInfoVar.isEmpty()) {
        contactInfoRepository.save(ContactInfo.builder()
                .resume(r)
                .telegram(null)
                .email(null)
                .phoneNumber(null)
                .facebook(null)
                .linkedIn(null)
                .build());

    } else {
        contactInfoRepository.save(ContactInfo.builder()
                .id(contactInfoVar.get().getId())
                .resume(r)
                .telegram(resumeDto.getContact().getTelegram())
                .email(resumeDto.getContact().getEmail())
                .phoneNumber(resumeDto.getContact().getPhoneNumber())
                .facebook(resumeDto.getContact().getFacebook())
                .linkedIn(resumeDto.getContact().getLinkedIn())
                .build());
    }
}
}
