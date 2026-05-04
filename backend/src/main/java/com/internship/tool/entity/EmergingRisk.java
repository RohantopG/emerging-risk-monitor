package com.internship.tool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "emerging_risk")
@EntityListeners(AuditingEntityListener.class)
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

    private String createdBy;
    private String updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean isDeleted = false;
}