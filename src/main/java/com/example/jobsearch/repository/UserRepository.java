package com.example.jobsearch.repository;

import com.example.jobsearch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getByEmail(String email);
}
