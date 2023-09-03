package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByJobApplicationId(Long jobApplicationId);
}
