package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor

public class ContactInfoService {
    private final ContactInfoDao contactInfoDao;
    public ContactInfoDto getAllContactInfoByResumeId (long resumeId) {

        return makeDtoFromContactInfo(contactInfoDao.getAllContactInfoByUserId(resumeId));
    }
    private ContactInfoDto makeDtoFromContactInfo(ContactInfo contactInfo) {
        return ContactInfoDto.builder()
                .id(contactInfo.getId())
                .resumeId(contactInfo.getResumeId())
                .telegram(contactInfo.getTelegram())
                .email(contactInfo.getEmail())
                .phoneNumber(contactInfo.getPhoneNumber())
                .facebookAccount(contactInfo.getFacebookAccount())
                .linkedinAccount(contactInfo.getLinkedinAccount())
                .build();
    }
    private ContactInfo createContactInfoFromDto (ContactInfoDto e) {
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
        contactInfoDao.createContactInfo(createContactInfoFromDto(e));
    }
    public void deleteContactInfo(ContactInfoDto e) {
        contactInfoDao.deleteContactInfo(createContactInfoFromDto(e));
    }
    public void editContactInfo(ContactInfoDto e) {
        contactInfoDao.editContactInfo(createContactInfoFromDto(e));
    }

}
