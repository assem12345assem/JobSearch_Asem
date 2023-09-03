package com.example.jobsearch.service;

import com.example.jobsearch.dao.ApplicantDao;
import com.example.jobsearch.dao.AuthorityDao;
import com.example.jobsearch.dao.EmployerDao;
import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.ApplicantDto;
import com.example.jobsearch.dto.EditProfileDto;
import com.example.jobsearch.dto.EmployerDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.model.Applicant;
import com.example.jobsearch.model.Employer;
import com.example.jobsearch.model.User;
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
    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final AuthorityDao authorityDao;
    private final EmployerDao employerDao;
    private final ApplicantDao applicantDao;
    private final FileService fileService;

    public void register(UserDto userDto) throws Exception {
        var u = userDao.find(userDto.getEmail());
        if (u.isEmpty()) {
            userDao.saveUser(User.builder()
                    .email(userDto.getEmail())
                    .phoneNumber(userDto.getPhoneNumber())
                    .userName(userDto.getUserName())
                    .userType(userDto.getUserType())
                    .password(encoder.encode(userDto.getPassword()))
                    .photo(userDto.getPhoto())
                    .enabled(Boolean.TRUE)
                    .build());
            authorityDao.save(userDto.getUserType().toUpperCase(), userDto.getEmail());
            if (userDto.getUserType().equalsIgnoreCase("applicant")) {
                applicantDao.createAtRegister(userDto.getEmail());
            }
            if (userDto.getUserType().equalsIgnoreCase("employer")) {
                employerDao.createAtRegister(userDto.getEmail());
            }
        } else {
            throw new Exception("User already exists");
        }
    }
    public UserDto getUserDto(Authentication auth) {
        org.springframework.security.core.userdetails.User userAuth = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User user = userDao.find(userAuth.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        return makeDtoFromUser(user);
    }
    public UserDto getUserDtoLocalStorage(String email) {
        User user = userDao.find(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        return makeDtoFromUser(user);
    }
    public UserDto getUserDtoTest(String email) {
        User user = userDao.find(email).orElseThrow(() -> new NoSuchElementException("User not found"));
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
        return applicantDao.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
    }
    public ResponseEntity<?> getProfile (Authentication auth) {
        UserDto u = getUserDto(auth);
        if(u.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = getApplicantByEmail(u.getEmail());
            return new ResponseEntity<>(makeDtoFromApplicant(a), HttpStatus.OK);
        } else {
            Employer e = employerDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
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
            Employer e = employerDao.findByEmail(u.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
            return new ResponseEntity<>(EmployerDto.builder().id(e.getId())
                    .companyName(e.getCompanyName()).build(), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getPhoto(String email) {
        User user = userDao.find(email).orElseThrow(() -> new NoSuchElementException("User not found"));
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
        User user = userDao.find(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        String fileName = fileService.saveUploadedFile(file, "images");
        userDao.savePhoto(email, fileName);
    }

    public void editProfile(String email, EditProfileDto editProfileDto) {
        User user = userDao.find(email).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!(editProfileDto.getPhoneNumber().isEmpty() || editProfileDto.getPhoneNumber() == null)) {
            user.setPhoneNumber(editProfileDto.getPhoneNumber());
        }

        if (!editProfileDto.getUserName().isEmpty() && editProfileDto.getUserName() != null) {
            user.setUserName(editProfileDto.getUserName());
        }

        userDao.update(user);
        if(user.getUserType().equalsIgnoreCase("applicant")) {
            Applicant a = applicantDao.findByEmail(user.getEmail()).orElseThrow(() -> new NoSuchElementException("Applicant not found"));
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

            applicantDao.update(a);
        } else{
            Employer e = employerDao.findByEmail(user.getEmail()).orElseThrow(() -> new NoSuchElementException("Employer not found"));
            if (!editProfileDto.getCompanyName().isEmpty() && editProfileDto.getCompanyName() != null) {
                e.setCompanyName(editProfileDto.getCompanyName());
            }
employerDao.update(e);
        }
    }
}
