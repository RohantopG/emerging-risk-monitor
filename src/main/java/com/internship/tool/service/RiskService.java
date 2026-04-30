package com.internship.tool.service;

import com.internship.tool.dto.RiskRequestDTO;
import com.internship.tool.dto.RiskResponseDTO;
import com.internship.tool.entity.EmergingRisk;
import com.internship.tool.repository.EmergingRiskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskService {

    private final EmergingRiskRepository repository;

    public RiskService(EmergingRiskRepository repository) {
        this.repository = repository;
    }

    // ➕ Create
    public RiskResponseDTO create(RiskRequestDTO req) {
        EmergingRisk risk = EmergingRisk.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .category(req.getCategory())
                .severity(req.getSeverity())
                .status(req.getStatus())
                .riskScore(req.getRiskScore())
                .identifiedDate(req.getIdentifiedDate())
                .dueDate(req.getDueDate())
                .ownerName(req.getOwnerName())
                .ownerEmail(req.getOwnerEmail())
                .build();

        return toDTO(repository.save(risk));
    }

    // 📄 Get all
    public List<RiskResponseDTO> getAll() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public org.springframework.data.domain.Page<RiskResponseDTO> getAllPaged(int page, int size) {

        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(page, size);

        return repository.findAll(pageable)
                .map(this::toDTO);
    }
    public List<RiskResponseDTO> getByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<RiskResponseDTO> getBySeverity(String severity) {
        return repository.findBySeverityIgnoreCase(severity)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // 📄 Get by id
    public RiskResponseDTO getById(Long id) {
        EmergingRisk risk = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));
        return toDTO(risk);
    }

    // ✏️ Update
    public RiskResponseDTO update(Long id, RiskRequestDTO req) {
        EmergingRisk risk = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));

        risk.setTitle(req.getTitle());
        risk.setDescription(req.getDescription());
        risk.setCategory(req.getCategory());
        risk.setSeverity(req.getSeverity());
        risk.setStatus(req.getStatus());
        risk.setRiskScore(req.getRiskScore());
        risk.setIdentifiedDate(req.getIdentifiedDate());
        risk.setDueDate(req.getDueDate());
        risk.setOwnerName(req.getOwnerName());
        risk.setOwnerEmail(req.getOwnerEmail());

        return toDTO(repository.save(risk));
    }

    // ❌ Delete (soft delete)
    public void delete(Long id) {
        EmergingRisk risk = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));

        risk.setIsDeleted(true);
        repository.save(risk);
    }

    // 🔄 Mapper
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
                .createdBy(r.getCreatedBy())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
}