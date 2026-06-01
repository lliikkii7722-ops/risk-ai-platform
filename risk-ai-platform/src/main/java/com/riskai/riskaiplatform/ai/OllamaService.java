package com.riskai.riskaiplatform.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OllamaService {

    @Value("${ollama.api.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateSuggestion(String prompt) {

        Map<String, Object> request = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false
        );

        Map response = restTemplate.postForObject(
                ollamaUrl,
                request,
                Map.class
        );

        if (response == null || response.get("response") == null) {
            return "No AI suggestion available";
        }

        return response.get("response").toString();
    }
}