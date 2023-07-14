package com.example.jobsearch.enums;

public enum UserType {
    APPLICANT ("applicant"),
    EMPLOYER ("employer");
    private String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
