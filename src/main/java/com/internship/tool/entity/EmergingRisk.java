package com.internship.tool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "emerging_risk")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergingRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean isDeleted;
}