package com.example.jobsearch.enums;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum UserType {
    EMPLOYER ("employer"),
    APPLICANT ("applicant");
    final String value;

    UserType(String value) {
        this.value = value;
    }

    public static UserType getType(String value) {
        if(value.equalsIgnoreCase(EMPLOYER.value)){
            return UserType.EMPLOYER;
        } else if (value.equalsIgnoreCase(APPLICANT.value)) {
            return UserType.APPLICANT;
        } else throw new NoSuchElementException("User type not found");
    }
}
