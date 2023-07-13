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
//    private final ResumeDao jrDao;
//    private final ContactInfoDao ciDao;
//    private final EducationDao edDao;
//    private final WorkExperienceDao weDao;
//    public List<Resume> getAllResumes() {
//        List<Resume> list = jrDao.getAllResumes();
//        for (Resume j:
//             list) {
//            completeResumeFields(j);
//        }
//        return list;
//    }
//    public List<Resume> getResumesByUserId(int userId) {
//        List<Resume> jr = jrDao.getAllResumesByUserId(userId);
//        for (Resume j:
//                jr) {
//            completeResumeFields(j);
//        }
//        return jr;
//    }
//    public List<Resume> getAllResumesByCategory(Category category) {
//        List<Resume> list = jrDao.getAllResumesByCategory(category);
//        for (Resume j:
//                list) {
//            completeResumeFields(j);
//        }
//        return list;
//    }
//    private void completeResumeFields(Resume jr) {
//        jr.setContactInfo(ciDao.getAllContactInfoByUserId(jr.getUserId()));
//        jr.setEducation(edDao.getAllEducationByResumeId(jr.getId()));
//        jr.setWorkExperience(weDao.getAllWorkExperienceByResumeId(jr.getId()));
//    }
}
