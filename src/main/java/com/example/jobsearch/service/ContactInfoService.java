package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dto.ContactInfoDto;
import com.example.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> createContactInfo(ContactInfoDto e) {
        if(e.getResumeId() == null) {
            log.warn("Cannot add contact info, resume does not exist");
            return new ResponseEntity<>("Cannot add contact info, resume does not exist", HttpStatus.NOT_FOUND);
        } else {
            contactInfoDao.save(createContactInfoFromDto(e));
            return new ResponseEntity<>("Contact info added successfully", HttpStatus.OK);

        }
    }

    public ResponseEntity<?>  deleteContactInfo(ContactInfoDto e) {
        var ci = contactInfoDao.findContactInfoById(e.getId());
        if(ci.isPresent()) {
            contactInfoDao.delete(e.getId());
            return new ResponseEntity<>("Contact info entry was deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Contact info entry does not exist", HttpStatus.OK);

        }
    }

    public ResponseEntity<?>  editContactInfo(ContactInfoDto e) {
        Long x = e.getId();
        if(x == null) {
            return new ResponseEntity<>("Contact Info id is not valid", HttpStatus.NOT_FOUND);
        } else {
            contactInfoDao.editContactInfo(createContactInfoFromDto(e));
            return new ResponseEntity<>("Contact Info was edited successfully", HttpStatus.OK);

        }
    }

}
