package com.example.jobsearch.service;


import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.entity.Applicant;
import com.example.jobsearch.entity.Employer;
import com.example.jobsearch.entity.Role;
import com.example.jobsearch.entity.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final ApplicantService applicantService;
    private final EmployerService employerService;
    private final RoleRepository roleRepository;
    private final AuthService authService;

    @Transactional
    public void register(UserDto userDto) {
        var u = userRepository.findById(userDto.getEmail());
        if (u.isEmpty()) {
            if (userDto.getUserType() != null) {
                try {
                    User user = userRepository.save(makeUserFromDto(userDto));
                    if (!userDto.getFile().isEmpty()) {
                        uploadUserPhoto(user.getEmail(), userDto.getFile());
                    }
                    Role role = roleRepository.findByRole("ROLE_" + user.getUserType().toUpperCase());
                    userRepository.assignRoleToUser(user.getEmail(), role.getId());
                    if (userDto.getUserType().equalsIgnoreCase("applicant")) {
                        applicantService.save(user);
                    }
                    if (userDto.getUserType().equalsIgnoreCase("employer")) {
                        employerService.save(user);
                    }
                } catch (Exception e) {
                    log.error("Error while registering user: {}", userDto.getEmail());
                }
            } else {
                log.info("User tried to register without user type: {}", userDto.getEmail());
                throw new IllegalArgumentException("Please select a user type to register.");
            }
        } else {
            throw new IllegalArgumentException("User already exists");
        }

    }

    public UserDto getUserDto(Authentication auth) {
        User userAuth = (User) auth.getPrincipal();
        User user = getUserByEmail(userAuth.getUsername());
        return makeDtoFromUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public UserDto getUserDtoLocalStorage(String email) {
        User user = getUserByEmail(email);
        return makeDtoFromUser(user);
    }

    public UserDto getUserDtoTest(String email) {
        User user = getUserByEmail(email);
        return makeDtoFromUser(user);
    }

    private User makeUserFromDto(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .userName(userDto.getUserName())
                .userType(userDto.getUserType())
                .password(encoder.encode(userDto.getPassword()))
                .photo(userDto.getPhoto())
                .enabled(Boolean.TRUE)
                .build();
    }

    private UserDto makeDtoFromUser(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userName(user.getUserName())
                .userType(user.getUserType())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .build();
    }

    public ResponseEntity<?> getProfileLocalStorage(String email) {
        UserDto u = getUserDtoLocalStorage(email);
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = applicantService.getApplicantByUserEmail(u.getEmail());
            return new ResponseEntity<>(applicantService.makeDtoFromApplicant(a), HttpStatus.OK);
        } else {
            Employer e = employerService.getEmployerByUserEmail(u.getEmail());
            return new ResponseEntity<>(EmployerDto.builder().id(e.getId())
                    .companyName(e.getCompanyName()).build(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getPhoto(String email) {
        User user = getUserByEmail(email);
        if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
            String extension = getFileExtension(user.getPhoto());
            if (extension != null && extension.equalsIgnoreCase("png")) {
                return fileService.getOutputFile(user.getPhoto(), "images", MediaType.IMAGE_PNG);
            } else if (extension != null && extension.equalsIgnoreCase("jpeg")) {
                return fileService.getOutputFile(user.getPhoto(), "images", MediaType.IMAGE_JPEG);
            }
        } else {
            return new ResponseEntity<>("Photo not found", HttpStatus.NOT_FOUND);
        }
        return null;
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        if (lastIndex != -1 && lastIndex < filename.length() - 1) {
            return filename.substring(lastIndex + 1);
        }
        return null;
    }

    public void uploadUserPhoto(String email, MultipartFile file) {
        User user = getUserByEmail(email);
        String fileName = fileService.saveUploadedFile(file, "images");
        user.setPhoto(fileName);
        userRepository.save(user);
    }

    public void editProfile(String email, EditProfileDto editProfileDto) {
        User user = getUserByEmail(email);
        if (editProfileDto != null && editProfileDto.getPhoneNumber() != null && !editProfileDto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(editProfileDto.getPhoneNumber());
        }

        if (editProfileDto.getUserName() != null && !editProfileDto.getUserName().isEmpty()) {
            user.setUserName(editProfileDto.getUserName());
        }

        userRepository.save(user);
        if (user.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = applicantService.getApplicantByUserEmail(user.getEmail());
            if (editProfileDto.getFirstName() != null && !editProfileDto.getFirstName().isEmpty()) {
                a.setFirstName(editProfileDto.getFirstName());
            }

            if (editProfileDto.getLastName() != null && !editProfileDto.getLastName().isEmpty()) {
                a.setLastName(editProfileDto.getLastName());
            }

            if (editProfileDto.getDateOfBirth() != null && !editProfileDto.getDateOfBirth().isEmpty()) {
                LocalDate dateOfBirth = LocalDate.parse(editProfileDto.getDateOfBirth());
                a.setDateOfBirth(dateOfBirth);
            }

            applicantService.saveApplicant(a);
        } else {
            Employer e = employerService.getEmployerByUserEmail(user.getEmail());
            if (editProfileDto.getCompanyName() != null && !editProfileDto.getCompanyName().isEmpty()) {
                e.setCompanyName(editProfileDto.getCompanyName());
            }
            employerService.saveEmployer(e);
        }
    }

    private void updateResetPasswordToken(String token, String email) {
        User user = getUserByEmail(email);
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    public UserDto getByResetPasswdToken(String email, String token) {
        User u = getUserByEmail(email);
        if (!u.getResetPasswordToken().equalsIgnoreCase(token)) {
            throw new IllegalArgumentException("Token not found");
        }
        return makeDtoFromUser(u);
    }

    public void updatePassword(UserDto userDto, String newPasswd) {
        User u = getUserByEmail(userDto.getEmail());
        u.setResetPasswordToken(null);
        u.setPassword(encoder.encode(newPasswd));
        userRepository.saveAndFlush(u);
    }

    public void makeResetPasswdLink(HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
    }

    public String getLanguage(String email) {
        User u = getUserByEmail(email);
        return u.getPreferredLanguage();
    }

    public void setLanguage(String email, String language) {
        User u = getUserByEmail(email);
        u.setPreferredLanguage(language);
        userRepository.save(u);
    }

    public boolean ifApplicant(Authentication auth) {
        try {
            UserDto user = authService.getAuthor(auth);

            if (user.getUserType().equalsIgnoreCase("applicant")) {
                return true;
            } else {
                throw new AccessDeniedException("Access is denied");
            }
        } catch (AccessDeniedException e) {
            log.warn("Access denied for user: " + auth.getName());
            return false;
        }
    }

    public String getToken(String email) {
        User user = getUserByEmail(email);
        return user.getResetPasswordToken();
    }
}
