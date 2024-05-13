package com.example.PROmpt.DTO;

import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.LLM;
import com.example.PROmpt.models.UserPrompt;
import jakarta.persistence.*;
import lombok.NonNull;

public class ResponseDTO {


    private String text;


    private Double grade;


    private Long llmID;

    private Long userPromptID;

    public ResponseDTO(String text, Double grade, Long llmID,Long userPromptID) {
        this.text = text;
        this.grade = grade;
        this.llmID = llmID;
        this.userPromptID=userPromptID;
    }


    public Long getUserPromptID() {
        return userPromptID;
    }

    public void setUserPromptID(Long userPromptID) {
        this.userPromptID = userPromptID;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getLlmID() {
        return llmID;
    }

    public void setLlmID(Long llmID) {
        this.llmID = llmID;
    }

}
