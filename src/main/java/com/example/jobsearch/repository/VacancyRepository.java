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
    List<Vacancy> findByEmployerId(Long employerId);
    Page<Vacancy> findAll(Pageable pageable);
    List<Vacancy> findAll();
    List<Vacancy> findByCategory(Category category, Sort sort);
//@Query("SELECT v FROM Vacancy v " +
//        "WHERE (:category is null or v.category = :category) " +
//        "AND (:targetDate is null or CAST(v.dateTime AS date) = :targetDate)")
//List<Vacancy> findByCategoryAndDate(@Param("category") Category category, @Param("targetDate") LocalDate targetDate, Sort sort);
@Query("SELECT v " +
        "FROM Vacancy v " +
        "WHERE (:category is null or v.category = :category) " +
        "AND (:targetDate is null or CAST(v.dateTime AS date) = :targetDate) " +
        "AND (:applicationCount is null " +
        "    OR :applicationCount = (SELECT COUNT(ja) FROM JobApplication ja WHERE ja.vacancy = v))")
List<Vacancy> findByCategoryAndDateAndApplication(
        @Param("category") Category category,
        @Param("targetDate") LocalDate targetDate,
        @Param("applicationCount") Integer applicationCount,
        Sort sort);
}
