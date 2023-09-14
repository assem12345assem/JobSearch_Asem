package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Category;
import com.example.jobsearch.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByEmployerIdAndIsActiveTrue(Long employerId);

    List<Vacancy> findByEmployerId(Long employerId);

    Page<Vacancy> findAll(Pageable pageable);

    List<Vacancy> findByIsActiveTrue();

    List<Vacancy> findByCategory(Category category, Sort sort);

    @Query("SELECT v " +
            "FROM Vacancy v " +
            "WHERE (:category is null or v.category = :category) " +
            "AND (:targetDate is null or CAST(v.dateTime AS date) = :targetDate) " +
            "AND (:applicationCount is null " +
            "    OR :applicationCount = (SELECT COUNT(ja) FROM JobApplication ja WHERE ja.vacancy = v)) " +
            "AND (:searchWord is null " +
            "    OR (:searchWord != 'default' AND (LOWER(v.vacancyName) LIKE CONCAT('%', LOWER(:searchWord), '%') " +
            "        OR CAST(v.salary AS STRING) LIKE CONCAT('%', LOWER(:searchWord), '%') " +
            "        OR LOWER(v.description) LIKE CONCAT('%', LOWER(:searchWord), '%') " +
            "        OR LOWER(v.category) LIKE CONCAT('%', LOWER(:searchWord), '%'))))" +
            "AND v.isActive = true")
    List<Vacancy> findByCategoryAndDateAndApplicationAndSearchWord(
            @Param("category") Category category,
            @Param("targetDate") LocalDate targetDate,
            @Param("applicationCount") Integer applicationCount,
            @Param("searchWord") String searchWord,
            Sort sort);
}
