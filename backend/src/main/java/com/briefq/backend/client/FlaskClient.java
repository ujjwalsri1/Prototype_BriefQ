package com.briefq.backend.client;

import com.briefq.backend.dto.ChatRequest;
import com.briefq.backend.dto.ChatResponse;
import com.briefq.backend.dto.SuggestRequest;
import com.briefq.backend.dto.SuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class FlaskClient {

    @Autowired
    private WebClient webClient;

//    public FlaskClient(WebClient webClient) {
//        this.webClient = webClient;
//    }

    public ChatResponse chat(ChatRequest request) {
        return webClient.post()
                .uri("/chat")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();
    }

    public SuggestResponse suggest(SuggestRequest request) {
        return webClient.post()
                .uri("/suggest")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SuggestResponse.class)
                .block();
    }
}