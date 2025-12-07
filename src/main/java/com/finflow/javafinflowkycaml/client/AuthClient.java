package com.finflow.javafinflowkycaml.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

@Component
public class AuthClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void updateKycStatus(Long userId, String status) {
        String url = "http://java-finflow-auth-service:9001/api/auth/user/" + userId + "/kyc";
        
        // Prepare payload
        Map<String, String> payload = Map.of("status", status);
        
        // Set JSON content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
        
        // Call Auth Service
        restTemplate.postForObject(url, request, String.class);
    }
}