package com.example.jobsearch.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {
    private Long id;
    private Long jobApplicationId;
    private String message;
    private String author;
    private LocalDateTime createTime;

}

