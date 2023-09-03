package com.example.jobsearch.service;

import com.example.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ApplicantProfileService applicantService;
    private final EmployerProfileService employerService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public Object getUserProfile(String userId) {
        User u = userService.getUserByEmail(userId);
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            return applicantService.getApplicantByUserId(u.getId());
        } else if (u.getUserType().equalsIgnoreCase("employer")) {
            return employerService.getEmployerByUserId(u.getId());
        } else throw new NoSuchElementException("User type is not found");
    }

    public List<?> getProfileContent(String userId) {
        User u = userService.getUserByEmail(userId);
        if (u.getUserType().equalsIgnoreCase("applicant")) {
            return resumeService.getAllResumesByApplicantId(applicantService.getApplicantByUserId(u.getId()).get().getId());
        } else if (u.getUserType().equalsIgnoreCase("employer")) {
            return vacancyService.getAllVacanciesByEmployerId(employerService.getEmployerByUserId(u.getId()).get().getId());
        } else throw new NoSuchElementException("User type is not found");
    }

    public void edit(String userId, String phoneNumber, String userName, String password, MultipartFile file, String companyName,
                     String firstName, String lastName, LocalDate date) {
        User u = userService.getUserByEmail(userId);
        String phone;
        if (phoneNumber.isEmpty()) phone = u.getPhoneNumber();
        else phone = phoneNumber;
        String uName;
        if (userName.isEmpty()) uName = u.getUserName();
        else uName = userName;
        String psw;
        if (password.isEmpty()) psw = u.getPassword();
        else psw = encoder.encode(password);
        UserDto maybeUserUpdate = UserDto.builder()
                .id(userId)
                .phoneNumber(phone)
                .userName(uName)
                .password(psw)
                .build();

        userService.edit(maybeUserUpdate);
        if (!file.isEmpty()) userService.uploadPhoto(userId, file);
        ApplicantDto maybeAppl = applicantService.getApplicantByUserId(userId).get();
        String fName;
        if (firstName.isEmpty()) fName = maybeAppl.getFirstName();
        else fName = firstName;
        String lName;
        if (lastName.isEmpty()) lName = maybeAppl.getLastName();
        else lName = lastName;
        LocalDate d;
        if (date == null) d = maybeAppl.getDateOfBirth();
        else d = date;
        if (userService.getUserByEmail(userId).getUserType().equalsIgnoreCase("applicant")) {
            applicantService.editApplicant(ApplicantDto.builder()
                    .id(maybeAppl.getId())
                    .userId(userId)
                    .firstName(fName)
                    .lastName(lName)
                    .dateOfBirth(d)
                    .build());
        } else {
            EmployerDto maybeEmpl = employerService.getEmployerByUserId(userId).get();
            String cName;
            if (companyName.isEmpty()) cName = maybeEmpl.getCompanyName();
            else cName = companyName;
            employerService.edit(EmployerDto.builder()
                    .id(maybeAppl.getId())
                    .userId(userId)
                    .companyName(cName)
                    .build());
        }

    }
}
