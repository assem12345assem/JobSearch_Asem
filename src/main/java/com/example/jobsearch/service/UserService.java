package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void someMethod(int userId) {
        Optional<User> mayByUser = userDao.getOptionalUserById(userId);
        mayByUser.ifPresent(e -> System.out.printf("%s, %s, %s%n", e.getId(), e.getLastName(), e.getPassword()));
    }
    public Optional<User> getUserById(int id) {
        return userDao.getOptionalUserById(id);
    }
    public Optional<User> getUserByFirstName(String firstName) {
        return userDao.getOptionalUserByFirstName(firstName);
    }
    public Optional<User> getOptionalUserByLastName(String lastName) {
        return userDao.getOptionalUserByLastName(lastName);
    }
    public Optional<User> getOptionalUserByEmail(String email) {
        return userDao.getOptionalUserByEmail(email);
    }
    public Optional<User> getOptionalUserByPhoneNumber(String phoneNumber) {
        return userDao.getOptionalUserByPhoneNumber(phoneNumber);
    }
    public boolean ifUserExists(String email) {
        return userDao.ifUserExists(email);
    }

}
