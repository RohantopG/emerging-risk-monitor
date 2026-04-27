package com.internship.tool.controller;

import com.internship.tool.service.RiskService;
import com.internship.tool.dto.RiskRequestDTO;
import com.internship.tool.dto.RiskResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risks")
public class EmergingRiskController {

    @Autowired
    private RiskService riskService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(riskService.getAll(0, 10));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RiskRequestDTO req) {
        return ResponseEntity.status(201).body(riskService.create(req));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        return ResponseEntity.ok(riskService.getStats());
    }
}