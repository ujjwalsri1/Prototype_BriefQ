package com.briefq.backend.service;

import com.briefq.backend.client.FlaskClient;
import com.briefq.backend.dto.ChatRequest;
import com.briefq.backend.dto.ChatResponse;
import com.briefq.backend.dto.SuggestRequest;
import com.briefq.backend.dto.SuggestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AIService {

    @Autowired
    private FlaskClient flaskClient;

//    public AIService(FlaskClient flaskClient) {
//        this.flaskClient = flaskClient;
//    }

    public ChatResponse getChat(ChatRequest request) {
        return flaskClient.chat(request);
    }

    public SuggestResponse getAdvice(SuggestRequest request) {
        return flaskClient.suggest(request);
    }
}