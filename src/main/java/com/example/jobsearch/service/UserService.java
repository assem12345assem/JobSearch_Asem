package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;


    public User getUserById(String email) {
        return userDao.getUserById(email);
    }

    public User getUserFromAuth(String auth) {
        int x = auth.indexOf("=");
        int y = auth.indexOf(",");
        return getUserById(auth.substring(x + 1, y));
    }

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
        u.setUserName(user.getUserName());
        u.setUserType(user.getUserType());
        u.setPassword(user.getPassword());
        u.setPhoto(user.getPhoto());
        u.setEnabled(user.isEnabled());

        return u;
    }

    private User makeUserFromDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .userName(user.getUserName())
                .userType(user.getUserType())
                .password(encoder.encode(user.getPassword()))
                .photo(user.getPhoto())
                .enabled(Boolean.TRUE)
                .build();
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

    public ResponseEntity<?> createUser(UserDto userDto) {
        User user = makeUserFromDto(userDto);
        if (!ifUserExists(userDto.getId())) {
            userDao.save(user);
            return new ResponseEntity<>("User was created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User already exists", HttpStatus.OK);
        }
    }


    public void editUser(UserDto userDto, Authentication auth) {
        var user = auth.getPrincipal();
        User u = getUserFromAuth(user.toString());
        if (u.getId().equalsIgnoreCase(userDto.getId())) {
            userDao.editUser(u);
        }
    }

    public ResponseEntity<?> uploadUserPhoto(String email, MultipartFile file, Authentication auth) {
        var user = auth.getPrincipal();
        User u = getUserFromAuth(user.toString());
        if (u.getId().equalsIgnoreCase(email)) {
            String fileName = fileService.saveUploadedFile(file, "images");
            userDao.savePhoto(email, fileName);
            return new ResponseEntity<>("Photo was uploaded successfully", HttpStatus.OK);
        } else {
            log.warn("Email and authentication id do not match: {}", u.getId());
            return new ResponseEntity<>("Email and authentication id do not match", HttpStatus.OK);
        }
    }

}
