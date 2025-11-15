package com.finflow.kyc.service;

import com.finflow.kyc.entity.KycDocument;
import com.finflow.kyc.repository.KycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KycService {
    private final KycRepository kycRepository;
    private final RestTemplate restTemplate;

    private final String AUTH_URL = "http://localhost:9001/api/auth";

    public KycDocument submitDocument(Long userId, String documentType, String documentUrl) {
        KycDocument doc = new KycDocument();
        doc.setUserId(userId);
        doc.setDocumentType(documentType);
        doc.setDocumentUrl(documentUrl);
        doc.setStatus("pending");
        doc.setSubmittedAt(LocalDateTime.now());
        return kycRepository.save(doc);
    }

    public void verifyDocument(Long docId, boolean approved) {
        KycDocument doc = kycRepository.findById(docId).orElseThrow();
        doc.setStatus(approved ? "verified" : "rejected");
        doc.setVerifiedAt(LocalDateTime.now());
        kycRepository.save(doc);

        // Reverse cascade to Auth
        String status = approved ? "verified" : "rejected";
        Map<String, Object> updates = Map.of("kycStatus", status);
        restTemplate.put(AUTH_URL + "/user/" + doc.getUserId(), updates);

        // Publish event
        // kafkaTemplate.send("user.kyc_updated", ...); if Kafka added
    }

    public String getKycStatus(Long userId) {
        List<KycDocument> docs = kycRepository.findByUserId(userId);
        if (docs.isEmpty()) return "pending";
        KycDocument latest = docs.get(docs.size() - 1);
        return latest.getStatus();
    }

    // Add AML check logic (simulate external API call)
}