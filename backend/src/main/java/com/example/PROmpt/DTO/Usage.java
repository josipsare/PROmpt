package com.example.PROmpt.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
