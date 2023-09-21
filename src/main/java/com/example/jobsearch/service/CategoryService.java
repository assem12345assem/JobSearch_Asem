package com.example.jobsearch.service;

import com.example.jobsearch.entity.Category;
import com.example.jobsearch.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getByName(String category) {
        return categoryRepository.findById(category).orElseThrow(() -> {
            log.warn("Category not found: {}", category);
            return new NoSuchElementException("Category not found");
        });
    }
}
