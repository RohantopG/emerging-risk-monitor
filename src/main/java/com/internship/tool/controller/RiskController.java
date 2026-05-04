package com.internship.tool.controller;

import com.internship.tool.dto.RiskRequestDTO;
import com.internship.tool.dto.RiskResponseDTO;
import com.internship.tool.service.RiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risks")
@CrossOrigin("*")
public class RiskController {

    private final RiskService service;

    public RiskController(RiskService service) {
        this.service = service;
    }

    // ➕ Create
    @PostMapping
    public ResponseEntity<RiskResponseDTO> create(@RequestBody RiskRequestDTO req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    // 📄 Get all
    @GetMapping
    public ResponseEntity<List<RiskResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // 📄 Get by id
    @GetMapping("/{id}")
    public ResponseEntity<RiskResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // ✏️ Update
    @PutMapping("/{id}")
    public ResponseEntity<RiskResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody RiskRequestDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    // ❌ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
    @GetMapping("/paged")
    public ResponseEntity<?> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(service.getAllPaged(page, size));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getByCategory(category));
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<?> getBySeverity(@PathVariable String severity) {
        return ResponseEntity.ok(service.getBySeverity(severity));
    }
}