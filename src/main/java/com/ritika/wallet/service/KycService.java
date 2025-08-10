package com.ritika.wallet.service;

import com.ritika.wallet.entity.KycDocument;
import com.ritika.wallet.entity.User;
import com.ritika.wallet.repo.KycRepository;
import com.ritika.wallet.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KycService {

    private final WalletService walletService;
    private final KycRepository kycRepository;
    private final UserRepository userRepository;

    @Transactional
    public KycDocument uploadDocument(Long userId, String docType, String docRef) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        KycDocument doc = KycDocument.builder()
                .userId(userId)
                .documentType(docType)
                .documentReference(docRef)
                .uploadedAt(LocalDateTime.now())
                .status("PENDING")
                .build();

        return kycRepository.save(doc);
    }

    public List<KycDocument> listByUser(Long userId) {
        return kycRepository.findByUserId(userId);
    }

    public Optional<KycDocument> getById(Long kycId) {
        return kycRepository.findById(kycId);
    }

    @Transactional
    public KycDocument approve(Long kycId) {
        KycDocument doc = kycRepository.findById(kycId)
                .orElseThrow(() -> new IllegalArgumentException("KYC document not found: " + kycId));

        doc.setStatus("APPROVED");

        doc.setVerifiedAt(LocalDateTime.now());
        kycRepository.save(doc);
        walletService.createWalletForUser(doc.getUserId());

        userRepository.findById(doc.getUserId()).ifPresent(user -> {
            user.setKycStatus("APPROVED");
            userRepository.save(user);
        });

        return doc;
    }

    @Transactional
    public KycDocument reject(Long kycId, String reason) {
        KycDocument doc = kycRepository.findById(kycId)
                .orElseThrow(() -> new IllegalArgumentException("KYC document not found: " + kycId));

        doc.setStatus("REJECTED");
        doc.setVerifiedAt(LocalDateTime.now());
        kycRepository.save(doc);

        userRepository.findById(doc.getUserId()).ifPresent(user -> {
            user.setKycStatus("REJECTED");
            userRepository.save(user);
        });

        return doc;
    }
}
