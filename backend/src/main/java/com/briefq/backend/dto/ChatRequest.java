package com.briefq.backend.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChatRequest {
    private String message;
    private String category;
}