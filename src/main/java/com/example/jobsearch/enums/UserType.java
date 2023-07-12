package com.example.jobsearch.enums;

public enum UserType {
    APPLICANT ("applicant"),
    EMPLOYER ("employer");

    String value;

    UserType(String value) {
        this.value = value;
    }
}
