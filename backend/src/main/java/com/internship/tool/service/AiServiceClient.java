package com.internship.tool.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AiServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String AI_URL = "http://localhost:5000/describe";

    public String describe(String text) {

        Map<String, String> body = new HashMap<>();
        body.put("text", text);

        try {
            return restTemplate.postForObject(
                    AI_URL,
                    body,
                    String.class
            );
        } catch (Exception e) {
            return "AI service failed";
        }
    }
}