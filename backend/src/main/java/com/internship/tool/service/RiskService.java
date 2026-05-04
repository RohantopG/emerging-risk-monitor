package com.internship.tool.service;

import com.internship.tool.entity.Risk;
import com.internship.tool.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;
    private final AiServiceClient aiServiceClient;

    public Risk create(Risk risk) {
        Risk saved = riskRepository.save(risk);
        processAI(saved.getId(), saved.getTitle());
        return saved;
    }

    public List<Risk> getAll() {
        return riskRepository.findAll();
    }

    public Risk getById(Long id) {
        return riskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));
    }

    public Risk update(Long id, Risk updated) {
        Risk risk = getById(id);
        risk.setTitle(updated.getTitle());
        risk.setDescription(updated.getDescription());
        risk.setStatus(updated.getStatus());
        return riskRepository.save(risk);
    }

    public void delete(Long id) {
        riskRepository.deleteById(id);
    }

    public List<Risk> search(String keyword) {
        return riskRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Risk> filterByStatus(String status) {
        return riskRepository.findByStatus(status);
    }

    public Page<Risk> getPaged(int page, int size) {
        return riskRepository.findAll(PageRequest.of(page, size));
    }

    public void handleOverdueRisks() {
        System.out.println("Scheduler running...");
    }

    public void processAI(Long id, String title) {
        try {
            String response = aiServiceClient.describe(title);
            Risk risk = getById(id);
            risk.setAiDescription(response);
            riskRepository.save(risk);
        } catch (Exception e) {
            System.out.println("AI failed");
        }
    }
}