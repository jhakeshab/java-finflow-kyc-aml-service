package com.finflow.kyc.controller;

import com.finflow.kyc.entity.KycDocument;
import com.finflow.kyc.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {
    private final KycService kycService;

    @PostMapping("/submit")
    public ResponseEntity<KycDocument> submit(@RequestParam Long userId, @RequestParam String documentType, @RequestParam String documentUrl) {
        return ResponseEntity.ok(kycService.submitDocument(userId, documentType, documentUrl));
    }

    @PostMapping("/verify/{docId}")
    public ResponseEntity<String> verify(@PathVariable Long docId, @RequestParam boolean approved) {
        kycService.verifyDocument(docId, approved);
        return ResponseEntity.ok("Document " + (approved ? "verified" : "rejected"));
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<String> getStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(kycService.getKycStatus(userId));
    }

    @PostMapping("/aml/check")
    public ResponseEntity<String> amlCheck(@RequestBody Map<String, String> req) {
        // Simulate AML
        return ResponseEntity.ok("clear");
    }
}