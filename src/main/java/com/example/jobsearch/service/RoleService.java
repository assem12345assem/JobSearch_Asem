package com.example.jobsearch.service;

import com.example.jobsearch.dao.RoleDao;
import com.example.jobsearch.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    public void insertRole(String userType, String email){
        roleDao.save(Role.builder()
                .role(userType.toUpperCase())
                .user_email(email)
                .build());
    }
}