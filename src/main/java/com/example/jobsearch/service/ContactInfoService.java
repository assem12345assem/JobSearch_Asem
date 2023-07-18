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
        ContactInfoDto c = new ContactInfoDto();
        c.setId(e.getId());
        c.setResumeId(e.getResumeId());
        c.setTelegram(e.getTelegram());
        c.setEmail(e.getEmail());
        c.setPhoneNumber(e.getPhoneNumber());
        c.setFacebookAccount(e.getFacebookAccount());
        c.setLinkedinAccount(e.getLinkedinAccount());
        return c;
    }

    private ContactInfo createContactInfoFromDto(ContactInfoDto e) {
        ContactInfo c = new ContactInfo();
        c.setId(e.getId());
        c.setResumeId(e.getResumeId());
        c.setTelegram(e.getTelegram());
        c.setEmail(e.getEmail());
        c.setPhoneNumber(e.getPhoneNumber());
        c.setFacebookAccount(e.getFacebookAccount());
        c.setLinkedinAccount(e.getLinkedinAccount());
        return c;
    }

    public void createContactInfo(ContactInfoDto e) {
        log.warn("Created contact info for resume: {}", e.getResumeId());
        contactInfoDao.createContactInfo(createContactInfoFromDto(e));
    }

    public void deleteContactInfo(ContactInfoDto e) {
        contactInfoDao.deleteContactInfo(createContactInfoFromDto(e));
    }

    public void editContactInfo(ContactInfoDto e) {
        contactInfoDao.editContactInfo(createContactInfoFromDto(e));
    }

}
