package com.internship.tool.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AiServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(AiServiceClient.class);
    private final RestTemplate restTemplate;
    
    @Value("${ai.service.url:http://localhost:5000}")
    private String aiServiceUrl;

    public AiServiceClient(RestTemplateBuilder restTemplateBuilder) {
        // Set 10s timeout
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Map<String, Object> checkHealth() {
        try {
            return restTemplate.getForObject(aiServiceUrl + "/health", Map.class);
        } catch (RestClientException e) {
            logger.error("Error calling health endpoint", e);
            return null; // Graceful null return on error
        }
    }

    public Map<String, Object> describe(Map<String, Object> requestData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
            return restTemplate.postForObject(aiServiceUrl + "/describe", request, Map.class);
        } catch (RestClientException e) {
            logger.error("Error calling describe endpoint", e);
            return null; // Graceful null return on error
        }
    }

    public Map<String, Object> generateReport(Map<String, Object> requestData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
            return restTemplate.postForObject(aiServiceUrl + "/generate-report", request, Map.class);
        } catch (RestClientException e) {
            logger.error("Error calling generate-report endpoint", e);
            return null; // Graceful null return on error
        }
    }
}
