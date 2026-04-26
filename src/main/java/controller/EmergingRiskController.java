package com.internship.tool.controller;

import com.internship.tool.dto.*;
import com.internship.tool.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risks")
@CrossOrigin("*")
public class EmergingRiskController {

    @Autowired
    private RiskService service;

    @GetMapping
    public ResponseEntity<Page<RiskResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<RiskResponseDTO> create(@RequestBody RiskRequestDTO req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody RiskRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}