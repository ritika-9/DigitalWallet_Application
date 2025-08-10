package com.ritika.wallet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType; // e.g., AADHAAR, PAN

    @Column(name = "document_reference", nullable = false, length = 300)
    private String documentReference; // filename or external id

    @Column(nullable = false, length = 20)
    private String status = "PENDING"; // PENDING / APPROVED / REJECTED

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}
