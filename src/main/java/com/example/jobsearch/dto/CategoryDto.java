package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private long id;
    private String category;
}