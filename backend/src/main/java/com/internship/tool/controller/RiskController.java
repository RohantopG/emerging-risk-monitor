package com.internship.tool.controller;

import com.internship.tool.entity.Risk;
import com.internship.tool.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risks")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService service;

    @PostMapping
    public Risk create(@RequestBody Risk risk) {
        return service.create(risk);
    }

    @GetMapping
    public List<Risk> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Risk getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Risk update(@PathVariable Long id, @RequestBody Risk risk) {
        return service.update(id, risk);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<Risk> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping("/filter")
    public List<Risk> filter(@RequestParam String status) {
        return service.filterByStatus(status);
    }

    @GetMapping("/paged")
    public Object paged(@RequestParam int page, @RequestParam int size) {
        return service.getPaged(page, size);
    }
}