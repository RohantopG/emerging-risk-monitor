package com.internship.tool.service;

import com.internship.tool.entity.Risk;
import com.internship.tool.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;
    private final EmailService emailService;
    private final AiServiceClient aiServiceClient;

    // CREATE
    public Risk create(Risk risk) {
        Risk saved = riskRepository.save(risk);

        try {
            emailService.sendRiskCreatedEmail("user@gmail.com", saved.getTitle());
        } catch (Exception e) {
            System.out.println("Email failed");
        }

        processAI(saved.getId(), saved.getTitle());

        return saved;
    }

    // GET ALL
    public List<Risk> getAll() {
        return riskRepository.findAll();
    }

    // GET BY ID
    public Risk getById(Long id) {
        return riskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));
    }

    // UPDATE
    public Risk update(Long id, Risk updated) {
        Risk risk = getById(id);

        risk.setTitle(updated.getTitle());
        risk.setDescription(updated.getDescription());
        risk.setStatus(updated.getStatus());

        return riskRepository.save(risk);
    }

    // DELETE
    public void delete(Long id) {
        riskRepository.deleteById(id);
    }

    // ---------------- AI ----------------

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

    // ---------------- OPTIONAL METHODS (fix errors) ----------------

    public List<Risk> getAllPaged(int page, int size) {
        return riskRepository.findAll(); // simple fix
    }

    public List<Risk> getByCategory(String category) {
        return riskRepository.findAll(); // placeholder
    }

    public List<Risk> getBySeverity(String severity) {
        return riskRepository.findAll(); // placeholder
    }

    public void handleOverdueRisks() {
        System.out.println("Scheduler running...");
    }
}