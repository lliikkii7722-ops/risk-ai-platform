package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.ai.OllamaService;
import com.riskai.riskaiplatform.dto.AiChatRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final OllamaService ollamaService;

    public AiController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody AiChatRequest request) {

        String prompt = """
                You are an AI assistant for a software project risk management system.
                Answer clearly and practically.

                Question: %s
                """.formatted(request.getQuestion());

        String answer = ollamaService.generateSuggestion(prompt);

        return Map.of("answer", answer);
    }
}