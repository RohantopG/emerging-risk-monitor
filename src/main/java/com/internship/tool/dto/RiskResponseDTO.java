package com.internship.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskResponseDTO {
    private Long id;
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
    private String aiDescription;
    private String aiCategory;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}