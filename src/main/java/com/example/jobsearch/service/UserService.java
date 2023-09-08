package com.example.jobsearch.service;


import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.entity.Applicant;
import com.example.jobsearch.entity.Employer;
import com.example.jobsearch.entity.User;
import com.example.jobsearch.repository.ApplicantRepository;
import com.example.jobsearch.repository.EmployerRepository;
import com.example.jobsearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final ApplicantRepository applicantRepository;
    private final EmployerRepository employerRepository;

    public void register(UserDto userDto) {
        var u = userRepository.findById(userDto.getEmail());
        if(u.isEmpty()) {
         User user =   userRepository.save(User.builder()
                    .email(userDto.getEmail())
                    .phoneNumber(userDto.getPhoneNumber())
                    .userName(userDto.getUserName())
                    .userType(userDto.getUserType())
                    .password(encoder.encode(userDto.getPassword()))
                    .photo(userDto.getPhoto())
                    .enabled(Boolean.TRUE)
                    .build());
//            authorityRepository.save(Authority.builder()
//                    .authority(userDto.getUserType().toUpperCase())
//                    .user(user).build());
            if (userDto.getUserType().equalsIgnoreCase("applicant")) {
                applicantRepository.save(Applicant.builder()
                        .user(user)
                        .build());
            }
            if (userDto.getUserType().equalsIgnoreCase("employer")) {
                employerRepository.save(Employer.builder()
                        .user(user)
                        .build());
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
    private ApplicantDto makeDtoFromApplicant(Applicant a) {
        return ApplicantDto.builder()
                .id(a.getId())
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .dateOfBirth(a.getDateOfBirth())
                .build();
    }
    private Applicant getApplicantByEmail (String email) {
        return applicantRepository.findByUserEmail(email).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
    }
    public ResponseEntity<?> getProfile (Authentication auth) {
        UserDto u = getUserDto(auth);
        if(u.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = getApplicantByEmail(u.getEmail());
            return new ResponseEntity<>(makeDtoFromApplicant(a), HttpStatus.OK);
        } else {
            Employer e = employerRepository.findByUserEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        return new ResponseEntity<>(EmployerDto.builder().id(e.getId())
                .companyName(e.getCompanyName()).build(), HttpStatus.OK);
        }
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
        if(u.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = getApplicantByEmail(u.getEmail());
            return new ResponseEntity<>(makeDtoFromApplicant(a), HttpStatus.OK);
        } else {
            Employer e = employerRepository.findByUserEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
            return new ResponseEntity<>(EmployerDto.builder().id(e.getId())
                    .companyName(e.getCompanyName()).build(), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getPhoto(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        if(!((user.getPhoto() == null) && !user.getPhoto().isEmpty())){
            String extension = getFileExtension(user.getPhoto());
            if (extension != null && extension.equalsIgnoreCase("png")) {
                return fileService.getOutputFile(user.getPhoto(), "images", MediaType.IMAGE_PNG);
            } else if(extension != null && extension.equalsIgnoreCase("jpeg")) {
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
        if(user.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = applicantRepository.findByUserEmail(user.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
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

            applicantRepository.save(a);
        } else{
            Employer e = employerRepository.findByUserEmail(user.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
            if (!editProfileDto.getCompanyName().isEmpty() && editProfileDto.getCompanyName() != null) {
                e.setCompanyName(editProfileDto.getCompanyName());
            }
employerRepository.save(e);
        }
    }
}
