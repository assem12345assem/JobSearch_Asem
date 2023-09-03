package com.example.jobsearch.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageListDto {
    private Long jobApplicationId;
    private String vacancyName;
    private String publisher;
    private String applicant;
    private Integer newMessage;
}
