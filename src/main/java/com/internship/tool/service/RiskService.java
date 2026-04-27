package com.internship.tool.service;

import com.internship.tool.dto.*;
import com.internship.tool.entity.EmergingRisk;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.EmergingRiskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RiskService {

    @Autowired
    private EmergingRiskRepository repository;

    // ✅ FIXED: method inside class
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        long total = repository.countByIsDeletedFalse();
        long open = repository.countByStatusAndIsDeletedFalse("OPEN");
        long high = repository.countBySeverityAndIsDeletedFalse("HIGH");

        stats.put("total", total);
        stats.put("open", open);
        stats.put("high", high);

        return stats;
    }

    public Page<RiskResponseDTO> getAll(int page, int size) {
        return repository.findByIsDeletedFalse(PageRequest.of(page, size))
                .map(this::toDTO);
    }

    public RiskResponseDTO getById(Long id) {
        EmergingRisk risk = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + id));
        return toDTO(risk);
    }

    public RiskResponseDTO create(RiskRequestDTO req) {
        validate(req);

        EmergingRisk risk = EmergingRisk.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .category(req.getCategory())
                .severity(req.getSeverity() != null ? req.getSeverity() : "MEDIUM")
                .status("OPEN")
                .riskScore(req.getRiskScore())
                .identifiedDate(req.getIdentifiedDate() != null ? req.getIdentifiedDate() : LocalDate.now())
                .dueDate(req.getDueDate())
                .ownerName(req.getOwnerName())
                .ownerEmail(req.getOwnerEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        return toDTO(repository.save(risk));
    }

    public RiskResponseDTO update(Long id, RiskRequestDTO req) {
        EmergingRisk risk = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + id));

        if (req.getTitle() != null) risk.setTitle(req.getTitle());
        if (req.getDescription() != null) risk.setDescription(req.getDescription());
        if (req.getCategory() != null) risk.setCategory(req.getCategory());
        if (req.getSeverity() != null) risk.setSeverity(req.getSeverity());
        if (req.getStatus() != null) risk.setStatus(req.getStatus());

        risk.setUpdatedAt(LocalDateTime.now());

        return toDTO(repository.save(risk));
    }

    public void delete(Long id) {
        EmergingRisk risk = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + id));

        risk.setIsDeleted(true);
        repository.save(risk);
    }

    private RiskResponseDTO toDTO(EmergingRisk r) {
        return RiskResponseDTO.builder()
                .id(r.getId())
                .title(r.getTitle())
                .description(r.getDescription())
                .category(r.getCategory())
                .severity(r.getSeverity())
                .status(r.getStatus())
                .riskScore(r.getRiskScore())
                .identifiedDate(r.getIdentifiedDate())
                .dueDate(r.getDueDate())
                .ownerName(r.getOwnerName())
                .ownerEmail(r.getOwnerEmail())
                .aiDescription(r.getAiDescription())
                .aiCategory(r.getAiCategory())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }

    private void validate(RiskRequestDTO req) {
        if (req.getTitle() == null || req.getTitle().isEmpty())
            throw new IllegalArgumentException("Title required");

        if (req.getCategory() == null || req.getCategory().isEmpty())
            throw new IllegalArgumentException("Category required");
    }
}