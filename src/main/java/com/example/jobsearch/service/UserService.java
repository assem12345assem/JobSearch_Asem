package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.enums.UserType;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final FileService fileService;

    public List<UserDto> getAllUsers() {
        log.warn("Used getAllUsers method");
        List<User> users = userDao.getAllUsers();
        return users.stream()
                .map(this::makeUserDtoFromUser)
                .toList();
    }

    public void someMethod(String userId) {
        Optional<User> mayByUser = userDao.getOptionalUserById(userId);
        mayByUser.ifPresent(e -> System.out.printf("%s, %s%n", e.getId(), e.getPassword()));
    }
    public ResponseEntity<?> getUserByEmail(String id) {
        Optional<User> maybeUser = userDao.getOptionalUserById(id);
        if(maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeUserDtoFromUser(maybeUser.get()), HttpStatus.OK);
    }

    private UserDto makeUserDtoFromUser(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .phoneNumber(user.getPhoneNumber())
                    .userType(returnEnum(user.getUserType()))
                    .password(user.getPassword())
                    .photo(user.getPhoto())
                    .build();
    }
    private User makeUserFromDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType().getValue())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .build();
    }
    private UserType returnEnum (String value) {
        if(value.equalsIgnoreCase("applicant")) {
            return UserType.APPLICANT;
        }
        return UserType.EMPLOYER;
    }
    public ResponseEntity<?> getOptionalUserByPhoneNumber(String phoneNumber) {
        Optional<User> maybeUser = userDao.getOptionalUserByPhoneNumber(phoneNumber);
        if(maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeUserDtoFromUser(maybeUser.get()), HttpStatus.OK);
    }
    public boolean ifUserExists(String email) {
        return userDao.ifUserExists(email);
    }

    public void createUser(UserDto userDto) {
        User user = makeUserFromDto(userDto);
        userDao.createUser(user);
    }


    public void editUser(UserDto userDto) {
        User user = makeUserFromDto(userDto);
        userDao.editUser(user);
    }
    public void uploadUserPhoto(String email, MultipartFile file) {
        String fileName = fileService.saveUploadedFile(file, "images");
        userDao.savePhoto(email, file.getName());
    }

}
