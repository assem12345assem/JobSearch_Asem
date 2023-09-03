package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserDto;
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
    private final UserDao userDao;

    public UserDto getAuthor(Authentication auth) {
        User user = (User) auth.getPrincipal();
        com.example.jobsearch.model.User fromDao = userDao.find(user.getUsername()).orElseThrow(() -> {
            log.warn("User not found: {}", user.getUsername());
            return new NoSuchElementException("User not found.");
        });
        return UserDto.builder().email(fromDao.getEmail()).phoneNumber(fromDao.getPhoneNumber()).userName(fromDao.getUserName()).userType(fromDao.getUserType()).password(fromDao.getPassword()).photo(fromDao.getPhoto()).build();
    }
}
