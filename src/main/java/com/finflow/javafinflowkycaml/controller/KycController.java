package com.finflow.javafinflowkycaml.controller;

import com.finflow.javafinflowkycaml.client.AuthClient;
import com.finflow.javafinflowkycaml.service.KycEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final AuthClient authClient;          // ← Injected
    private final KycEventPublisher kycEventPublisher;

    // Constructor injection
    public KycController(AuthClient authClient, KycEventPublisher kycEventPublisher) {
        this.authClient = authClient;
        this.kycEventPublisher = kycEventPublisher;
    }

    @PostMapping("/verify")
    public Map<String, String> verify(@RequestBody Map<String, Object> payload) {
        Object userIdObj = payload.get("user_id");
        Long userId;
        if (userIdObj instanceof Number) {
            userId = ((Number) userIdObj).longValue();
        } else if (userIdObj instanceof String) {
            userId = Long.parseLong((String) userIdObj);
        } else {
            throw new IllegalArgumentException("user_id must be a number or string");
        }
        
        String status = (String) payload.get("status");
        
        // 1. Update Auth Service user record
        authClient.updateKycStatus(userId, status);
        
        // 2. Publish Kafka event
        kycEventPublisher.publishKycUpdate(userId, status);
        
        return Map.of("status", "KYC verification completed");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "up");
    }
}