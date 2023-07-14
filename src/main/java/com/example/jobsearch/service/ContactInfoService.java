package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.model.ContactInfo;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

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
    public void createContactInfo(ContactInfo e) {
        contactInfoDao.createContactInfo(e);
    }
    public void deleteContactInfo(ContactInfo e) {
        createContactInfo(e);
    }
    public void editContactInfo(ContactInfo e) {
        contactInfoDao.editContactInfo(e);
    }

}
