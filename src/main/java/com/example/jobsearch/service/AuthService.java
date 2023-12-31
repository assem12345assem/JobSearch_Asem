package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;


    public UserDto getAuthor(Authentication auth) {
        User u = (User) auth.getPrincipal();
        String email = u.getUsername();
        com.example.jobsearch.entity.User fromDao = userRepository.findById(email).orElseThrow(() -> {
            log.warn("User not found: {}", email);
            return new NoSuchElementException("User not found.");
        });
        return UserDto.builder().email(fromDao.getEmail()).phoneNumber(fromDao.getPhoneNumber()).userName(fromDao.getEmail()).userType(fromDao.getUserType()).password(fromDao.getPassword())
                .photo(fromDao.getPhoto())
                .build();
    }

}
