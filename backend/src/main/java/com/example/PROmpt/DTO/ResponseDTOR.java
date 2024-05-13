package com.example.PROmpt.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDTOR {
    private String finishedPromptText;
    private Integer promptTokens;
    private Integer responseTokens;
    private String responseFromLLMText;
    private String userPromptText;

    public ResponseDTOR(String finishedPromptText, String responseFromLLMText, String userPromptText) {
        this.finishedPromptText = finishedPromptText;
        this.responseFromLLMText = responseFromLLMText;
        this.userPromptText = userPromptText;
    }

    @Override
    public String toString() {
        return "ResponseDTOR{" +
                "finishedPromptText='" + finishedPromptText + '\'' +
                ", responseFromLLMText='" + responseFromLLMText + '\'' +
                '}';
    }
}
