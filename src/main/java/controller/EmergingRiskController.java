package com.internship.tool.controller;

import com.internship.tool.entity.EmergingRisk;
import com.internship.tool.repository.EmergingRiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/risks")
@CrossOrigin(origins = "*")
public class EmergingRiskController {

    @Autowired
    private EmergingRiskRepository riskRepository;

    @GetMapping
    public ResponseEntity<Page<EmergingRisk>> getAllRisks(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDir
    ) {
        Sort sort = sortDir.equals("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable =
                PageRequest.of(page, size, sort);
        return ResponseEntity.ok(
                riskRepository
                        .findByIsDeletedFalse(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergingRisk> getRiskById(
            @PathVariable Long id
    ) {
        return riskRepository
                .findByIdAndIsDeletedFalse(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmergingRisk> createRisk(
            @RequestBody EmergingRisk risk
    ) {
        risk.setStatus("OPEN");
        risk.setIsDeleted(false);
        if (risk.getIdentifiedDate() == null) {
            risk.setIdentifiedDate(LocalDate.now());
        }
        if (risk.getSeverity() == null) {
            risk.setSeverity("MEDIUM");
        }
        EmergingRisk saved = riskRepository.save(risk);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergingRisk> updateRisk(
            @PathVariable Long id,
            @RequestBody EmergingRisk updatedData
    ) {
        return riskRepository
                .findByIdAndIsDeletedFalse(id)
                .map(existing -> {
                    if (updatedData.getTitle() != null)
                        existing.setTitle(
                                updatedData.getTitle());
                    if (updatedData.getDescription()
                            != null)
                        existing.setDescription(
                                updatedData
                                        .getDescription());
                    if (updatedData.getCategory() != null)
                        existing.setCategory(
                                updatedData.getCategory());
                    if (updatedData.getSeverity() != null)
                        existing.setSeverity(
                                updatedData.getSeverity());
                    if (updatedData.getStatus() != null)
                        existing.setStatus(
                                updatedData.getStatus());
                    if (updatedData.getRiskScore() != null)
                        existing.setRiskScore(
                                updatedData.getRiskScore());
                    if (updatedData.getDueDate() != null)
                        existing.setDueDate(
                                updatedData.getDueDate());
                    if (updatedData.getOwnerName() != null)
                        existing.setOwnerName(
                                updatedData.getOwnerName());
                    if (updatedData.getOwnerEmail()
                            != null)
                        existing.setOwnerEmail(
                                updatedData
                                        .getOwnerEmail());
                    return ResponseEntity.ok(
                            riskRepository.save(existing));
                })
                .orElse(ResponseEntity
                        .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisk(
            @PathVariable Long id
    ) {
        return riskRepository
                .findByIdAndIsDeletedFalse(id)
                .map(risk -> {
                    risk.setIsDeleted(true);
                    riskRepository.save(risk);
                    return ResponseEntity
                            .noContent().<Void>build();
                })
                .orElse(ResponseEntity
                        .notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmergingRisk>> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                riskRepository.searchByKeyword(
                        q, PageRequest.of(page, size)));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total",
                riskRepository.countByIsDeletedFalse());
        stats.put("open",
                riskRepository
                        .countByStatusAndIsDeletedFalse(
                                "OPEN"));
        stats.put("inProgress",
                riskRepository
                        .countByStatusAndIsDeletedFalse(
                                "IN_PROGRESS"));
        stats.put("resolved",
                riskRepository
                        .countByStatusAndIsDeletedFalse(
                                "RESOLVED"));
        stats.put("critical",
                riskRepository
                        .countBySeverityAndIsDeletedFalse(
                                "CRITICAL"));
        stats.put("high",
                riskRepository
                        .countBySeverityAndIsDeletedFalse(
                                "HIGH"));
        return ResponseEntity.ok(stats);
    }
}