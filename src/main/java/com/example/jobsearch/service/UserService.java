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
import com.example.jobsearch.util.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
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
    private final EmailService emailService;

    @Transactional
    public void register(UserDto userDto) {
        var u = userRepository.findById(userDto.getEmail());
        if (u.isEmpty()) {
            if (userDto.getUserType() != null) {
                System.out.println(userDto.getUserType());
                try {
                    User user = userRepository.save(makeUserFromDto(userDto));
                    if(userDto.getFile() != null) {
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
                    log.error("Error while registering user: {}", u.get().getEmail());
                }
            } else {
                log.info("User tried to register without user type: {}", u.get().getEmail());
                throw new IllegalArgumentException("Please select a user type to reigser.");
            }
        } else {
            throw new IllegalArgumentException("User already exists");
        }

    }

    public UserDto getUserDto(Authentication auth) {
        User userAuth = (User) auth.getPrincipal();
        User user = userRepository.findById(userAuth.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        return makeDtoFromUser(user);
    }

    public UserDto getUserDtoLocalStorage(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        return makeDtoFromUser(user);
    }

    public UserDto getUserDtoTest(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
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
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!((user.getPhoto() == null) && !user.getPhoto().isEmpty())) {
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
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        String fileName = fileService.saveUploadedFile(file, "images");
        user.setPhoto(fileName);
        userRepository.save(user);
    }

    public void editProfile(String email, EditProfileDto editProfileDto) {
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!(editProfileDto.getPhoneNumber().isEmpty() || editProfileDto.getPhoneNumber() == null)) {
            user.setPhoneNumber(editProfileDto.getPhoneNumber());
        }

        if (!editProfileDto.getUserName().isEmpty() && editProfileDto.getUserName() != null) {
            user.setUserName(editProfileDto.getUserName());
        }

        userRepository.save(user);
        if (user.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = applicantService.getApplicantByUserEmail(user.getEmail());
            if (!editProfileDto.getFirstName().isEmpty() && editProfileDto.getFirstName() != null) {
                a.setFirstName(editProfileDto.getFirstName());
            }

            if (!editProfileDto.getLastName().isEmpty() && editProfileDto.getLastName() != null) {
                a.setLastName(editProfileDto.getLastName());
            }

            if (!editProfileDto.getDateOfBirth().isEmpty() && editProfileDto.getDateOfBirth() != null) {
                LocalDate dateOfBirth = LocalDate.parse(editProfileDto.getDateOfBirth());
                a.setDateOfBirth(dateOfBirth);
            }

            applicantService.saveApplicant(a);
        } else {
            Employer e = employerService.getEmployerByUserEmail(user.getEmail());
            if (!editProfileDto.getCompanyName().isEmpty() && editProfileDto.getCompanyName() != null) {
                e.setCompanyName(editProfileDto.getCompanyName());
            }
            employerService.saveEmployer(e);
        }
    }

    private void updateResetPasswordToken(String token, String email) {
        User user = userRepository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    public UserDto getByResetPasswdToken(String token) {
        User u = userRepository.findByResetPasswordToken(token).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return makeDtoFromUser(u);
    }

    public void updatePassword(UserDto userDto, String newPasswd) {
        User u = userRepository.getByEmail(userDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        u.setResetPasswordToken(null);
        u.setPassword(encoder.encode(newPasswd));
        userRepository.saveAndFlush(u);
    }

    public void makeResetPasswdLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteUrl(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
    }
}
