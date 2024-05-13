package com.example.PROmpt.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseFromChat {
    private String response;
    private Integer promptTokens;
    private Integer responseTokens;
}
