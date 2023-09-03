package com.example.jobsearch.repository;

import com.example.jobsearch.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
    Optional<ContactInfo> findByResumeId(Long resumeId);
}
