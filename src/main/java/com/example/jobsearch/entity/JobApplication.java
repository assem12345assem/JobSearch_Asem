package com.example.jobsearch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "jobApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Message> messages;
}

