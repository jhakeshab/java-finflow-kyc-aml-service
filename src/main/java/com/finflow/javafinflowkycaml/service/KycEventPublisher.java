package com.finflow.javafinflowkycaml.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KycEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KycEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishKycUpdate(Long userId, String status) {
        String event = String.format("{\"user_id\":%d,\"status\":\"%s\"}", userId, status);
        kafkaTemplate.send("user.kyc_updated", event);
        System.out.println("Published KYC update: " + event);
    }
}

