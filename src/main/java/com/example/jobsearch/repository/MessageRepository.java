package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
