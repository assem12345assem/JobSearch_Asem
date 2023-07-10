package com.example.jobsearch.service;

import com.example.jobsearch.dao.ContactInfoDao;
import com.example.jobsearch.dao.EducationDao;
import com.example.jobsearch.dao.JobseekerResumeDao;
import com.example.jobsearch.dao.WorkExperienceDao;
import com.example.jobsearch.model.ContactInfo;
import com.example.jobsearch.model.JobseekerResume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobseekerResumerService {
    private final JobseekerResumeDao jrDao;
    private final ContactInfoDao ciDao;
    private final EducationDao edDao;
    private final WorkExperienceDao weDao;
    public List<JobseekerResume> getAllJobseekerResumes() {
        List<JobseekerResume> list = jrDao.getAllJobseekerResumes();
        for (JobseekerResume j:
             list) {
            completeResumeFields(j, j.getUserId());
        }
        return list;
    }
    public List<JobseekerResume> getJobseekerResumeByUserId(int userId) {
        List<JobseekerResume> jr = jrDao.getAllJobseekerResumesByUserId(userId);
        for (JobseekerResume j:
                jr) {
            completeResumeFields(j, j.getUserId());
        }
        return jr;
    }
    private void completeResumeFields(JobseekerResume jr, int userId) {
        jr.setContactInfo(ciDao.getAllContactInfoByUserId(userId));
        jr.setEducation(edDao.getAllEducationByUserId(userId));
        jr.setWorkExperience(weDao.getAllWorkExperienceByUserId(userId));
    }
}
