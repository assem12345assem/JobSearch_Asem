package com.example.jobsearch.service;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.Employer;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final FileService fileService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final ApplicantProfileService applicantService;
    private final EmployerProfileService employerService;



    public List<UserDto> getAllUsers() {
        log.warn("Used getAllUsers method");
        List<User> users = userDao.getAllUsers();
        return users.stream()
                .map(this::makeUserDtoFromUser)
                .toList();
    }

    public ResponseEntity<?> findUserByEmail(String id) {
        Optional<User> maybeUser = userDao.getOptionalUserById(id);
        if (maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(makeUserDtoFromUser(maybeUser.get()), HttpStatus.OK);
    }
    public User getUserByEmail(String id) {
        Optional<User> maybeUser = userDao.getOptionalUserById(id);
    if(maybeUser.isPresent()) return maybeUser.get();
    else throw new NoSuchElementException("User does not exist");
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
        Optional<User> u = getUserFromAuth(user.toString());
        if (u.get().getId().equalsIgnoreCase(userDto.getId())) {
            userDao.editUser(u.get());
        }
    }
    public void edit(UserDto userDto) {

            userDao.editUser(makeUserFromDto(userDto));

    }
    public ResponseEntity<?> editEmployer(EmployerDto employerDto, Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = getUserFromAuth(u.toString());

        if(employerService.ifEmployerExists(employerDto.getUserId())) {
            if(user.get().getId().equalsIgnoreCase(employerDto.getUserId())) {
                Employer employer = employerService.makeEmployerFromDto(employerDto);
                employerService.editEmployer(employer);
                return new ResponseEntity<>("Employer was edited successfully", HttpStatus.OK);
            } else {
                log.warn("User tried to edit another employer's profile: {} {}", user.get().getId(), employerDto.getUserId());
                return new ResponseEntity<>("Error: attempt to edit other user's profile", HttpStatus.NOT_FOUND);
            }
        } else {
            log.warn("EDIT EMPLOYER Error: Employer does not exist {}", employerDto.getUserId());
            return new ResponseEntity<>("Employer does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> uploadUserPhoto(String email, MultipartFile file, Authentication auth) {
        var user = auth.getPrincipal();
        Optional<User> u = getUserFromAuth(user.toString());
        if (u.get().getId().equalsIgnoreCase(email)) {
            String fileName = fileService.saveUploadedFile(file, "images");
            userDao.savePhoto(email, fileName);
            return new ResponseEntity<>("Photo was uploaded successfully", HttpStatus.OK);
        } else {
            log.warn("Email and authentication id do not match: {}", u.get().getId());
            return new ResponseEntity<>("Email and authentication id do not match", HttpStatus.OK);
        }
    }
    public void uploadPhoto(String email, MultipartFile file) {

            String fileName = fileService.saveUploadedFile(file, "images");
            userDao.savePhoto(email, fileName);

    }

    public List<UserDto> getUsersByUserType(String userType) {
        List<User> list = userDao.getUsersByUserType(userType);
        return list.stream()
                .map(this::makeUserDtoFromUser)
                .toList();
    }

    private String defaultPhoto(String userType) {
        if (userType.equalsIgnoreCase("employer")) {
            return "default_company.png";
        } else {
            return "default_user.png";
        }
    }

    public void register(UserDto userDto) throws Exception {
        var user = userDao.getUserById(userDto.getId());

        if (user.isEmpty()) {
            userDao.save(User.builder().id(userDto.getId()).phoneNumber(userDto.getPhoneNumber()).userName(userDto.getUserName()).userType(userDto.getUserType()).password(encoder.encode(userDto.getPassword())).photo(defaultPhoto(userDto.getUserType())).enabled(Boolean.TRUE).build());
            roleService.insertRole(userDto.getUserType(), userDto.getId());
            if (userDto.getUserType().equalsIgnoreCase("applicant")) {
                applicantService.saveApplicant(ApplicantDto.builder()
                        .userId(userDto.getId())
                        .build());
            } else {
                employerService.saveEmployer(EmployerDto.builder()
                        .userId(userDto.getId())
                        .build());
            }
        } else {
            throw new Exception("User already exists");
        }
    }



    public Optional<User>getUserFromAuth(String auth) {
        int x = auth.indexOf("=");
        int y = auth.indexOf(",");
        var user = userDao.getUserById(auth.substring(x + 1, y));

        if (user.isEmpty()) {
            throw new NoSuchElementException("Could not authenticate the user");
        } else return user;

    }

//    public String login(UserDto userDto) {
//        var user = userDao.getUserById(userDto.getId());
//        if(user.isPresent()) return userDto.getId();
//        else return null;
//    }
    public String login(Authentication auth) {
        var u = auth.getPrincipal();
        Optional<User> user = getUserFromAuth(u.toString());
        return user.map(User::getId).orElse(null);
    }

    
}
