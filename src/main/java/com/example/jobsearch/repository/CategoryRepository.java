package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
