package com.example.jobsearch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="categories")
public class Category {
    @Id
    private String category;

    @OneToMany(mappedBy = "category")
    List<Resume> resumes;

    @OneToMany(mappedBy = "category")
    List<Vacancy> vacancies;
}
