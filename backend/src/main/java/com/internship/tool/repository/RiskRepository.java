package com.internship.tool.repository;

import com.internship.tool.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskRepository extends JpaRepository<Risk, Long> {

    List<Risk> findByTitleContainingIgnoreCase(String keyword);

    List<Risk> findByStatus(String status);
}