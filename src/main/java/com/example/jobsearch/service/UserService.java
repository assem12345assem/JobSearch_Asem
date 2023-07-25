package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    public User getUserById(String email) {
        return userDao.getUserById(email);
    }
public User getUserFromAuth(String auth) {
    int x = auth.indexOf("=");
    int y = auth.indexOf(",");
    return getUserById(auth.substring(x+1, y));
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
        u.setUserType(user.getUserType());
        u.setPassword(user.getPassword());
        u.setPhoto(user.getPhoto());

        return u;
    }

    private User makeUserFromDto(UserDto user) {
        return User.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType())
                .password(user.getPassword())
                .photo(user.getPhoto())
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

    public void createUser(UserDto userDto) {

        User user = makeUserFromDto(userDto);
        userDao.createUser(user);
    }


    public void editUser(UserDto userDto, Authentication auth) {
        var user = auth.getPrincipal();
        User u = getUserFromAuth(user.toString());
        if (u.getId().equalsIgnoreCase(userDto.getId())) {
            userDao.editUser(u);
        }
    }

    public void uploadUserPhoto(String email, MultipartFile file, Authentication auth) {
        var user = auth.getPrincipal();
        User u = getUserFromAuth(user.toString());
        if (u.getId().equalsIgnoreCase(email)) {
            String fileName = fileService.saveUploadedFile(file, "images");
            userDao.savePhoto(email, fileName);
        }
    }

}
