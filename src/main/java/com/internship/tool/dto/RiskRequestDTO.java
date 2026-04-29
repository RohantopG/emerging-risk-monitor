package com.internship.tool.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RiskRequestDTO {
    private String title;
    private String description;
    private String category;
    private String severity;
    private String status;
    private Integer riskScore;
    private LocalDate identifiedDate;
    private LocalDate dueDate;
    private String ownerName;
    private String ownerEmail;
}