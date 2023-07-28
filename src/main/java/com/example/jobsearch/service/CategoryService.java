package com.example.jobsearch.service;

import com.example.jobsearch.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    public Optional<String> getCategory(String category) {
        return categoryDao.getCategory(category);
    }
}
