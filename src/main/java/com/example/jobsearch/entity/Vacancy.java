package com.example.jobsearch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;
    private String vacancyName;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
    private Integer salary;
    private String description;
    private Integer requiredExperienceMin;
    private Integer requiredExperienceMax;
    private boolean isActive;
    private boolean isPublished;

    @UpdateTimestamp
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "vacancy")
    private List<JobApplication> jobApplications;
}

