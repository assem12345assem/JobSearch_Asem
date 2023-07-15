package com.example.jobsearch.enums;

public enum UserType {
    APPLICANT ("applicant"),
    EMPLOYER ("employer");
    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
