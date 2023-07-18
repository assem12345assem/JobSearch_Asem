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

    public CategoryDto getCategoryById(long id) {
        Category category = categoryDao.getCategoryById(id);
        CategoryDto c = new CategoryDto();
        c.setId(category.getId());
        c.setCategory(category.getCategory());
        return c;
    }
    public Long getIdByCategory(String category) {
        return categoryDao.getIdByCategory(category);
    }
}
