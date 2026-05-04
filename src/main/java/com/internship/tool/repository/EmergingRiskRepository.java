package com.internship.tool.repository;

import com.internship.tool.entity.EmergingRisk;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EmergingRiskRepository extends JpaRepository<EmergingRisk, Long> {

    // Optional: hide deleted records
    List<EmergingRisk> findByCategoryIgnoreCase(String category);
    List<EmergingRisk> findBySeverityIgnoreCase(String severity);
    List<EmergingRisk> findByIsDeletedFalse();
}