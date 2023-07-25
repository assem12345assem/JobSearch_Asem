package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ContactInfoService {
    private final ContactInfoDao contactInfoDao;

    public ContactInfoDto getAllContactInfoByResumeId(long resumeId) {
        return makeDtoFromContactInfo(contactInfoDao.getAllContactInfoByUserId(resumeId));
    }

    private ContactInfoDto makeDtoFromContactInfo(ContactInfo e) {
        return ContactInfoDto.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .telegram(e.getTelegram())
                .email(e.getEmail())
                .phoneNumber(e.getPhoneNumber())
                .facebookAccount(e.getFacebookAccount())
                .linkedinAccount(e.getLinkedinAccount())
                .build();
    }

    private ContactInfo createContactInfoFromDto(ContactInfoDto e) {
        return ContactInfo.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .telegram(e.getTelegram())
                .email(e.getEmail())
                .phoneNumber(e.getPhoneNumber())
                .facebookAccount(e.getFacebookAccount())
                .linkedinAccount(e.getLinkedinAccount())
                .build();
    }

    public void createContactInfo(ContactInfoDto e) {
        log.warn("Created contact info for resume: {}", e.getResumeId());
        contactInfoDao.save(createContactInfoFromDto(e));
    }

    public void deleteContactInfo(ContactInfoDto e) {
        contactInfoDao.delete(e.getId());
    }

    public void editContactInfo(ContactInfoDto e) {
        contactInfoDao.editContactInfo(createContactInfoFromDto(e));
    }

}
