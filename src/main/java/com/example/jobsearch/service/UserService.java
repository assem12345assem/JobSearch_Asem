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

    public ResponseEntity<?> getUserByEmail(String id) {
        Optional<User> maybeUser = userDao.getOptionalUserById(id);
        if (maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeUserDtoFromUser(maybeUser.get()), HttpStatus.OK);
    }

    private UserDto makeUserDtoFromUser(User user) {
        UserDto u = new UserDto();
        u.setId(user.getId());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setUserType(returnEnum(user.getUserType()));
        u.setPassword(user.getPassword());
        u.setPhoto(user.getPhoto());

        return u;
    }

    private User makeUserFromDto(UserDto user) {
        User u = new User();
        u.setId(user.getId());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setUserType(user.getUserType().getValue());
        u.setPassword(user.getPassword());
        u.setPhoto(user.getPhoto());

        return u;
    }

    private UserType returnEnum(String value) {
        if (value.equalsIgnoreCase("applicant")) {
            return UserType.APPLICANT;
        }
        return UserType.EMPLOYER;
    }

    public ResponseEntity<?> getOptionalUserByPhoneNumber(String phoneNumber) {
        Optional<User> maybeUser = userDao.getOptionalUserByPhoneNumber(phoneNumber);
        if (maybeUser.isEmpty()) {
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
        userDao.savePhoto(email, fileName);
    }

}
