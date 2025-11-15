package com.finflow.kyc.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_documents")
@Data
public class KycDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String documentType; // id/passport
    private String documentUrl;
    private String status = "pending"; // pending/verified/rejected
    private LocalDateTime submittedAt;
    private LocalDateTime verifiedAt;
}