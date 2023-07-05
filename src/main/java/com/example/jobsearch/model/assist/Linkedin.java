package com.example.jobsearch.model.assist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Linkedin {
    private String linkedInAccount;
    private String workExperience;
    private String education;
}
