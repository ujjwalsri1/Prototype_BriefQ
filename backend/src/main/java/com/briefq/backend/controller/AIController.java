package com.briefq.backend.controller;

import com.briefq.backend.dto.ChatRequest;
import com.briefq.backend.dto.ChatResponse;
import com.briefq.backend.dto.SuggestRequest;
import com.briefq.backend.dto.SuggestResponse;
import com.briefq.backend.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    @Autowired
    private  AIService aiService;

//    public AIController(AIService aiService) {
//        this.aiService = aiService;
//    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(aiService.getChat(request));
    }

    @PostMapping("/suggest")
    public ResponseEntity<SuggestResponse> suggest(@RequestBody SuggestRequest request) {
        return ResponseEntity.ok(aiService.getAdvice(request));
    }
}