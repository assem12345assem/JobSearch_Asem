package com.example.jobsearch.service;

import com.example.jobsearch.dao.CategoryDao;
import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryDto getCategoryByName(String name) {
        Category category = categoryDao.getCategoryByName(name);
        CategoryDto c = new CategoryDto();
        c.setCategory(category.getCategory());
        return c;
    }
}
