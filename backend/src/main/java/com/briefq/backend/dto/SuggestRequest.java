package com.briefq.backend.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SuggestRequest {
    private String text;
    private String category;
}