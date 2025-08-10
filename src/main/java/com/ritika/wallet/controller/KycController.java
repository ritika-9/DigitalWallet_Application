package com.ritika.wallet.controller;

import com.ritika.wallet.dto.KycUploadRequest;
import com.ritika.wallet.entity.KycDocument;
import com.ritika.wallet.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody KycUploadRequest req) {
        try {
            KycDocument doc = kycService.uploadDocument(req.getUserId(), req.getDocumentType(), req.getDocumentReference());
            return ResponseEntity.ok(doc);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listByUser(@PathVariable Long userId) {
        List<KycDocument> list = kycService.listByUser(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{kycId}")
    public ResponseEntity<?> getById(@PathVariable Long kycId) {
        return kycService.getById(kycId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body((KycDocument) Map.of("error", "KYC not found")));
    }

    @PostMapping("/approve/{kycId}")
    public ResponseEntity<?> approve(@PathVariable Long kycId) {
        try {
            kycService.approve(kycId);
            return ResponseEntity.ok(Map.of("message", "KYC approved successfully."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/reject/{kycId}")
    public ResponseEntity<?> reject(@PathVariable Long kycId, @RequestBody(required = false) Map<String,String> body) {
        try {
            String reason = body != null ? body.getOrDefault("reason", "") : "";
            kycService.reject(kycId, reason);
            return ResponseEntity.ok(Map.of("message", "KYC rejected successfully."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
