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
@Table(name="resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;
    private String resumeTitle;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
    private Integer expectedSalary;
    private boolean isActive;
    private boolean isPublished;

    @UpdateTimestamp
    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContactInfo contactInfo;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkExperience> workExperienceList;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "resume")
    private List<JobApplication> jobApplications;
}
