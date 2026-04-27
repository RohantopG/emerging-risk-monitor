package com.internship.tool.repository;

import com.internship.tool.entity.EmergingRisk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmergingRiskRepository
        extends JpaRepository<EmergingRisk, Long> {

    Page<EmergingRisk> findByIsDeletedFalse(Pageable pageable);

    Optional<EmergingRisk> findByIdAndIsDeletedFalse(Long id);

    List<EmergingRisk> findByStatusAndIsDeletedFalse(String status);

    List<EmergingRisk> findByCategoryAndIsDeletedFalse(String category);

    List<EmergingRisk> findBySeverityAndIsDeletedFalse(String severity);

    @Query("SELECT r FROM EmergingRisk r WHERE r.dueDate < :today AND r.status NOT IN ('RESOLVED','CLOSED') AND r.isDeleted = false")
    List<EmergingRisk> findOverdueRisks(@Param("today") LocalDate today);

    @Query("SELECT r FROM EmergingRisk r WHERE r.dueDate BETWEEN :today AND :deadline AND r.status NOT IN ('RESOLVED','CLOSED') AND r.isDeleted = false")
    List<EmergingRisk> findRisksDueSoon(@Param("today") LocalDate today,
                                        @Param("deadline") LocalDate deadline);

    @Query("SELECT r FROM EmergingRisk r WHERE r.isDeleted = false AND (LOWER(r.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%',:keyword,'%')))")
    Page<EmergingRisk> searchByKeyword(@Param("keyword") String keyword,
                                       Pageable pageable);

    Long countByStatusAndIsDeletedFalse(String status);
    Long countBySeverityAndIsDeletedFalse(String severity);
    Long countByIsDeletedFalse();
}