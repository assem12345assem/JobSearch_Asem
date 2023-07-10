package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeDao jrDao;
    private final ContactInfoDao ciDao;
    private final EducationDao edDao;
    private final WorkExperienceDao weDao;
    public List<Resume> getAllResumes() {
        List<Resume> list = jrDao.getAllResumes();
        for (Resume j:
             list) {
            completeResumeFields(j, j.getUserId());
        }
        return list;
    }
    public List<Resume> getResumesByUserId(int userId) {
        List<Resume> jr = jrDao.getAllResumesByUserId(userId);
        for (Resume j:
                jr) {
            completeResumeFields(j, j.getUserId());
        }
        return jr;
    }
    private void completeResumeFields(Resume jr, int userId) {
        jr.setContactInfo(ciDao.getAllContactInfoByUserId(userId));
        jr.setEducation(edDao.getAllEducationByUserId(userId));
        jr.setWorkExperience(weDao.getAllWorkExperienceByUserId(userId));
    }
}
