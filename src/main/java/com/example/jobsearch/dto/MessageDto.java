package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageDto {
    private Long id;
    private Long jobApplicationId;
    private String message;
    private String author;
    private String createTime;

}

